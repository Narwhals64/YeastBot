package narwhals64.YeastBot.CardGames.GameTypes;

import narwhals64.YeastBot.CardGames.Card;
import narwhals64.YeastBot.CardGames.Cards.StandardCard;
import narwhals64.YeastBot.CardGames.GamePlayer;
import narwhals64.YeastBot.CardGames.Pile;
import narwhals64.YeastBot.CardGames.PileTypes.Deck;
import narwhals64.YeastBot.CardGames.PileTypes.MessyPile;
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

    private MessyPile roundPile;
    private Deck discard;

    private Guild guild;
    private TextChannel channel;

    private int highestCard; // card with the highest value.  Default is 3.  Everything below it, looping around from 2-Ace-King, is "lesser" than the highest card value
    private boolean currentlyPlaying;
    private int curPlayerIndex;
    private int cardMinValue; // the current minimum value for a card to be played.  By default, played cards must be HIGHER than this value.
    private boolean allowDupeValues; // regarding the previous int, does this game allow players to play a card equal to the last card played?  By default, no.

    public Pondscum(GuildMessageReceivedEvent event) {
        super("Pondscum Game #" + (GAME_INDEX_COUNTER+1));

        host = new PondscumPlayer(event.getAuthor().getId());
        players = new ArrayList<>();
        waitingList = new ArrayList<>();
        waitingList.add(host);
        allPlayersNormal = true;

        roundPile = new MessyPile();
        discard = new Deck();

        guild = event.getGuild();
        channel = event.getChannel();

        highestCard = 3;
        currentlyPlaying = false;
        curPlayerIndex = 0;
        cardMinValue = 0;
        allowDupeValues = false;
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

            while (deck.getAmt() >= players.size()) { // while cards can still be evenly distributed amongst players
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

    /**
     * Send a message, pinging the player whose turn it currently is.
     */
    public void announcePlayerTurn() {
        if (currentlyPlaying) {
            String output = "";
            output += "```" + getName();
            output += "Game discard pile: " + discard.toString() + "\n";
            output += "This round's discard pile: " + roundPile.toString() + "\n";
            output += "\n";
            output += guild.getMemberById(players.get(curPlayerIndex).getId()).getAsMention() + "'s turn - play a card " + getMinCardValueName() + " or higher!\n";
            channel.sendMessage(output).queue();
        }
        else
            channel.sendMessage("The game is not currently in play or it is nobody's turn.").queue();
    }
    private String getMinCardValueName() {
        return StandardCard.getFullNameFromInt(cardMinValue);
    }




    public void enterCommand(GuildMessageReceivedEvent event) {
        String com = event.getMessage().getContentRaw();

        if (com.equalsIgnoreCase("start") && event.getAuthor().getId().equals(host.getId())) { // if host entered start
            initializeRound();
        } // "start" --> Start the game.
        else if (com.equalsIgnoreCase("hand") || com.equalsIgnoreCase("h")) {
            event.getAuthor().openPrivateChannel().queue((channel) -> {
                channel.sendMessage("```" + getPlayer(event.getAuthor()).getHand().toString() + "```").queue();
            }); // Send a message displaying the player's hand.
        } // "h" --> Whisper your hand to you.
        else if (com.equalsIgnoreCase("turn") || com.equalsIgnoreCase("t")) {
            announcePlayerTurn();
        } // "t" --> Show whose turn it is.
        else if (event.getAuthor().getId().equals(players.get(curPlayerIndex).getId())){ // consider anything else to be playing cards as long as it's the right player.
            String[] wantToPlay = com.split("\\s+"); // separate the command by spaces.

            PondscumPlayer player = getPlayer(event.getAuthor());
            Pile cardsToBePlayed = new Pile();


            for (String s : wantToPlay) {
                cardsToBePlayed.addCard(player.getHand().takeCard(s));
            } // make a pile out of the cards you are attempting to play.  if a card is not available, it will be a null value.

            boolean canBePlayed = true;

            for (int i = 0 ; i < cardsToBePlayed.getSize() ; i++) {
                if (cardsToBePlayed.get(i) == null)
                    canBePlayed = false;
            } // check if any of the cards are null.  If they are null then the pile cannot be played.

            if (!cardsToBePlayed.allCardsSimilarRank()) {
                canBePlayed = false;
            } // check if the cards are of similar rank.  If they are not then they cannot be played.



            if (canBePlayed) {
                while (cardsToBePlayed.getSize() > 0) {
                    discard.topDeck(cardsToBePlayed.remove(0));
                }
                curPlayerIndex = (curPlayerIndex + 1)%players.size();
            } // if the cards can be played, then play them and proceed to the next player's turn.
            else {
                while (cardsToBePlayed.getSize() != 0)
                    player.getHand().giveCard(cardsToBePlayed.remove(0));
                channel.sendMessage("Sorry, but those cards could not be played.").queue();
            } // if the cards cannot be played, then return them to the player's hand.



        } // Anything else --> Play cards (if cur player).
    }

    /**
     * Test a value to see if it's acceptable via the rules of Pondscum
     * @param n Value to be tested.
     * @return true if the given value is "greater" than the current required value, i.e. acceptable.
     */
    private boolean psAcceptableValue(int n) {
        int min = cardMinValue;
        int max = highestCard;
        if (min == max)
            return n == min;
        if (min > max)
            max += 13;
        return false; // TODO
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
