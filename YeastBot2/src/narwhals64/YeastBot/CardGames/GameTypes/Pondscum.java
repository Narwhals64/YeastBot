package narwhals64.YeastBot.CardGames.GameTypes;

import narwhals64.YeastBot.CardGames.Card;
import narwhals64.YeastBot.CardGames.Cards.StandardCard;
import narwhals64.YeastBot.CardGames.GamePlayer;
import narwhals64.YeastBot.CardGames.PileTypes.Deck;
import narwhals64.YeastBot.CardGames.PlayerTypes.PondscumPlayer;
import narwhals64.YeastBot.GameInstance;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Collections;

public class Pondscum extends GameInstance {
    private PondscumPlayer host;
    private ArrayList<PondscumPlayer> players; // at first, this is not ordered.  After the first game, it is ordered.  First is prez, last is pondscum
    private ArrayList<PondscumPlayer> waitingList; // waiting list for new players.  Each new player pushes the rest of the players up, thus becoming the next Pondscum.
    private boolean allPlayersNormal; // for the first game, all players are considered normal.

    Guild guild;
    TextChannel channel;

    private boolean currentlyPlaying;
    private int curPlayerIndex;

    public Pondscum(GuildMessageReceivedEvent event) {
        super("Pondscum Game #" + (GAME_INDEX_COUNTER+1));

        host = new PondscumPlayer(event.getAuthor().getId());
        players = new ArrayList<>();
        waitingList = new ArrayList<>();
        allPlayersNormal = true;

        guild = event.getGuild();
        channel = event.getChannel();

        currentlyPlaying = false;
        curPlayerIndex = 0;
    }

    /**
     * Add a player to the waiting list.
     * @param newPlayer PondscumPlayer to be added.
     */
    public void addPlayer(PondscumPlayer newPlayer) {
        waitingList.add(newPlayer);
    }

    /**
     * Create a player from a Discord user, then add it to the waiting list.
     * Do not add if there is already a player with that ID.
     * @param event Event that added the player
     */
    public void addPlayer(GuildMessageReceivedEvent event) {
        PondscumPlayer newPlayer = new PondscumPlayer(event.getAuthor().getId());
        boolean doNotAdd = false;

        if (host.getId().equals(newPlayer.getId()))
            doNotAdd = true;

        for (GamePlayer gp : players)
            if (gp.getId().equals(newPlayer.getId()))
                doNotAdd = true;

        for (GamePlayer gp : waitingList)
            if (gp.getId().equals(newPlayer.getId()))
                doNotAdd = true;

        if (!doNotAdd) {
            addPlayer(new PondscumPlayer(newPlayer.getId().toString()));
            event.getChannel().sendMessage("A player was successfully added to the Waiting List!  You will join in the next round.").queue();
        }
        else {
            event.getChannel().sendMessage("The player could not be added to the Waiting List.  You may already be in the game!").queue();
        }
    }

    /**
     * Find a player in the players ArrayList using a Discord User.
     * @param user Discord User object.
     * @return PondscumPlayer object.
     */
    private PondscumPlayer getPlayer(User user) {
        for (PondscumPlayer pp : players) {
            if (pp.getId().equals(user.getId())) {
                return pp;
            }
        }
        return null;
    }


    /**
     * Insert all players in the waiting list into the player list.
     * Each player is moved to the bottom of the player list, making them automatically Pondscum.
     */
    public void joinPlayers() {
        int wlsize = waitingList.size();
        for (int i = 0 ; i < wlsize ; i++) {
            players.add(waitingList.get(0)); // Move the first person from the waiting list to the bottom of the players list, thus making them pondscum.
            if (players.size() == 1) // if there's one player then make him host
                host = players.get(0);
            waitingList.remove(0); // now remove the player from the waitingList since they are in the game.
        }
    }

    /**
     * Start the round, as in set it up for play.  One round refers to the playing of the cards until a new roster of pres, VP, etc. is created.
     */
    public void initializeRound() {

        joinPlayers(); // first, make everyone join the player list

        if (true) {
            Deck deck = new Deck();
            deck.topDeck(new StandardCard(0, 1));
            deck.topDeck(new StandardCard(0, 2));

            deck.shuffle();

            while (deck.getAmt() > players.size()) { // while cards can still be evenly distributed amongst players
                for (PondscumPlayer pp : players) {
                    pp.getHand().giveCard(deck.drawCard());
                }
            }

        } // second, distribute cards amongst players


        currentlyPlaying = true;

        if (allPlayersNormal) { // if this is the first round then everyone is a normal and does not have to trade cards.
            Collections.shuffle(players);
            allPlayersNormal = false;
        } // third, either start the game without trading (if it's the first round) or ...
        else { // otherwise, players must trade cards

            String presId = players.get(0).getId(); // save president ID because pres goes first

            Collections.shuffle(players);

            int presIndex = 0;
            for (int i = 0 ; i < players.size() ; i++)
                if (players.get(i).getId().equals(presId))
                    presIndex = i;

            curPlayerIndex = presIndex;

        } // ... or trade and make the president go first.
        channel.sendMessage("Pondscum has now started!\nWe will start on the President's turn.").queue();


    }

    public void announcePlayerTurn() {
        if (currentlyPlaying)
            channel.sendMessage("It is " + guild.getMemberById(players.get(curPlayerIndex).getId()) + "'s turn!").queue();
        else
            channel.sendMessage("The game is not currently in play.").queue();
    }

    private void playCards(String com) {

    }



    public void enterCommand(GuildMessageReceivedEvent event) {
        String com = event.getMessage().getContentRaw();

        if (com.equalsIgnoreCase("start") && event.getAuthor().getId().equals(host.getId())) { // if host entered start
            initializeRound();
        } // "start" --> Start the game.
        else if (com.equalsIgnoreCase("hand") || com.equalsIgnoreCase("h")) {
            event.getAuthor().openPrivateChannel().queue((channel) ->
            {
                channel.sendMessage(getPlayer(event.getAuthor()).getHand().toString()).queue();
            });
        } // "h" --> Whisper your hand to you.
        else if (com.equalsIgnoreCase("turn") || com.equalsIgnoreCase("t")) {
            announcePlayerTurn();
        } // "t" --> Show whose turn it is.
        else { // consider anything else to be playing cards
            String[] cardsToBePlayed = com.split("\\s+"); // separate the command by spaces.
            
            for (String s : cardsToBePlayed) {

            } // consider each string -- can they all be considered cards, and are they all of the same type?
        } // Anything else --> Play cards.
    }



    public String displayCurrentState() {
        String output = "";

        output += "Host: " + guild.getMemberById(host.getId()).getNickname() + ".\n";

        output += "Other players: ";
        for (GamePlayer gp : players) {
            if (!gp.getId().equals(host.getId()))
                output += guild.getMemberById(gp.getId()).getNickname() + " ";
        }
        output += "\n";
        for (GamePlayer gp : waitingList) {
            output += guild.getMemberById(gp.getId()).getNickname() + " ";
        }

        output += "In the waiting list: ";
        output += "\n";

        return output;

    }


}
