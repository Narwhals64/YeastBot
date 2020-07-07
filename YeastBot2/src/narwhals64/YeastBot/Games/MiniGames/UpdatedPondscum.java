package narwhals64.YeastBot.Games.MiniGames;

import narwhals64.YeastBot.Games.Resources.Cards.StandardCard;
import narwhals64.YeastBot.Games.Resources.GamePlayer;
import narwhals64.YeastBot.Games.Resources.PileTypes.Deck;
import narwhals64.YeastBot.Games.Resources.PileTypes.MessyPile;
import narwhals64.YeastBot.GameInstance;
import narwhals64.YeastBot.YeastBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;
import java.util.Collections;

public class UpdatedPondscum extends GameInstance {

    private PondscumPlayer host; // The man behind the Pondscum.  The host allows the game to start.

    private ArrayList<PondscumPlayer> finishedPlayers;
    private ArrayList<PondscumPlayer> players;
    private ArrayList<PondscumPlayer> waitingList;

    private int buyIn; // The amount of Crumbs needed to play the game.
    private int pool; // The total amount of Crumbs put into the pool.
    private boolean allowMorePlayers; // If the game is for Crumbs, it cannot be joined after the first round.
    private int round;

    private Guild guild;
    private TextChannel channel;

    private MessyPile heatPile;
    private Deck discardPile;

    private boolean currentlyPlaying;
    private int curPlayerIndex;
    private int highestCard; // card with the highest value.  Default is 3.  Everything below it, looping around from 2-Ace-King, is "lesser" than the highest card value
    private int curMinValue; // the current minimum value for a card to be played.  Played cards must be this value or higher.
    private int cardAmtNeeded; // the amount of cards needed to play in this heat.
    private int lastPlay; // the index of the last player to play a card


    public UpdatedPondscum(GuildMessageReceivedEvent event) {
        super("Pondscum Game #" + GAME_INDEX_COUNTER+1);

        host = new PondscumPlayer(event.getAuthor().getId());

        finishedPlayers = new ArrayList<>();
        players = new ArrayList<>();
        waitingList = new ArrayList<>();

        buyIn = 0;
        pool = 0;
        allowMorePlayers = true;
        round = -1;

        guild = event.getGuild();
        channel = event.getChannel();

        heatPile = new MessyPile();
        discardPile = new Deck();
        discardPile.clean();

        currentlyPlaying = false;
        curPlayerIndex = 0;
        highestCard = 3;
        curMinValue = 4;
        cardAmtNeeded = 0;
        lastPlay = -1;
    }


    /**
     * Add a player to the waiting list.
     * @param newPlayer PondscumPlayer to be added.
     */
    private void addPlayer(PondscumPlayer newPlayer) {
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

        if (round == -1) {
            doNotAdd = true;
        } // players cannot join during round -1.

        if (!doNotAdd) if (host.getId().equals(newPlayer.getId())) {
            doNotAdd = true;
        } // if the player is the host, do not add

        if (!doNotAdd) for (GamePlayer gp : finishedPlayers) {
            if (gp.getId().equals(newPlayer.getId())) {
                doNotAdd = true;
                break;
            }
        } // if the player is already in the finished list, do not add

        if (!doNotAdd) for (GamePlayer gp : players) {
            if (gp.getId().equals(newPlayer.getId())) {
                doNotAdd = true;
                break;
            }
        } // if the player is already in the players list, do not add

        if (!doNotAdd) for (GamePlayer gp : waitingList) {
            if (gp.getId().equals(newPlayer.getId())) {
                doNotAdd = true;
                break;
            }
        } // if the player is already in the waiting list, do not add

        if (!doNotAdd) if (!allowMorePlayers) {
            doNotAdd = true;
        } // if the game is not allowing more players due to betting, do not add

        if (!doNotAdd) if (YeastBot.getProfile(event).getCrumbs() < buyIn) {
            doNotAdd = true;
        } // if the player does not have enough Crumbs to enter, do not add

        if (!doNotAdd) {
            addPlayer(new PondscumPlayer(newPlayer.getId()));
            if (buyIn == 0) {
                event.getChannel().sendMessage(event.getGuild().getMemberById(event.getAuthor().getId()).getAsMention() + " has been added to the Waiting List!  You will join in the next round.").queue();
            } // announce they have joined
            else {
                event.getChannel().sendMessage(event.getGuild().getMemberById(event.getAuthor().getId()).getAsMention() + " has been added to the Waiting List!  **The buy-in is " + buyIn + " Crumbs.**  You will join in the next round.").queue();
            } // OR announce they have joined and bought in.
        } // announce they could be added.
        else {
            event.getChannel().sendMessage(event.getGuild().getMemberById(event.getAuthor().getId()).getAsMention() + ", you were not able to be put into the game.  You may already be in the game, not have enough Crumbs to join, or the game is not allowing more players.").queue();
        } // announce they could not be added.
    }


    /**
     * Reads a command and decides what to do with it.
     * @param event Command to be read.
     */
    public void enterCommand(GuildMessageReceivedEvent event) {
        String com = event.getMessage().getContentRaw();

        if (host.getId().equals(event.getAuthor().getId()) && com.equalsIgnoreCase("start")) {
            startGame();
        } // "start" --> start the game
        else if (host.getId().equals(event.getAuthor().getId()) && com.equalsIgnoreCase("edit")) {

        }
        else if (com.equalsIgnoreCase("turn") || com.equalsIgnoreCase("t")) {
            viewTurn();
        } // "t" --> check turn details
        else if (com.equalsIgnoreCase("view") || com.equalsIgnoreCase("v")) {
            viewGame();
        } // "v" --> check game details and current status
        else if (com.equalsIgnoreCase("hand") || com.equalsIgnoreCase("h")) {

        } // "h" --> check your hand
        else if (com.equalsIgnoreCase("forfeit") || com.equalsIgnoreCase("surrender")) {

        } // "forfeit" --> leave the game
        else if (players.get(curPlayerIndex).getId().equals(event.getAuthor().getId())) {
            if (com.equalsIgnoreCase("pass") || com.equalsIgnoreCase("p") || com.equalsIgnoreCase("skip")) {

            } // "p" --> pass your turn
            else {

            }
        } // pass or play both require you to be the current player

    }

    /**
     * Attempts to start the game.
     * Changes all variables to make the game able to be started and increases the round number by 1.
     */
    private void startGame() {
        if (currentlyPlaying) {
            channel.sendMessage("The game could not be started.").queue();
        } // cannot start the game
        else if (round == -1) {
            round++;
        } // game editing phase --> lobby phase
        else if (round == 0) {

            boolean canStart = true;

            if (buyIn > 0) {
                for (int i = 0 ; i < players.size() ; i++) {
                    if (YeastBot.getProfile(players.get(i).getId()).getCrumbs() < buyIn) {
                        channel.sendMessage(guild.getMemberById(players.get(i).getId()).getAsMention() + " did not have enough Crumbs to play and was thus removed.  Point and laugh at the poor boy.").queue();
                        players.remove(i);
                        i--;
                    } // if a player cannot afford the game, simply remove them (and call them poor).
                }
            } // Remove all players who cannot afford to play.

            if (players.size() < 2) {
                canStart = false;
            } // If there are not enough players, do not start.

            if (canStart) {
                round++;
                takeCrumbs();
                startGame();
            } // Start the game if possible.
            else {
                channel.sendMessage("The game could not be started, for one reason or another.").queue();
            } // Tell the channel the game could not be started.

        } // lobby phase --> playing phase.  This can fail and not start the game.
        else {
            joinPlayers(); // first, make everyone join the player list

            cleanPlayers(); // second, clean all players

            if (true) {

                Deck deck = new Deck();
                deck.topDeck(new StandardCard(0, 1).flip());
                deck.topDeck(new StandardCard(0, 2).flip());

                deck.shuffle();
                deck.flipPile();

                while (deck.getSize() >= players.size()) { // while cards can still be evenly distributed amongst players
                    for (PondscumPlayer pp : players) {
                        pp.getHand().giveCard(deck.draw());
                    }
                }
                for (PondscumPlayer pp : players) {
                    pp.getHand().sortHand((highestCard%13)+1);
                }

            } // third, distribute cards to players

            if (true) {

                currentlyPlaying = true;
                resetCardMinValue();
                cardAmtNeeded = 0;
                lastPlay = -1;
                round++;

            } // fourth, reset all variables for game start


            if (round == 1) { // if this is the first round then everyone is a normal and does not have to trade cards.
                Collections.shuffle(players);
                curPlayerIndex = 0;

                channel.sendMessage("On the first round, there is no hierarchy.  Good luck!").queue();
            } // fifth, either start the game without trading (if it's the first round) or ...
            else {
                Collections.shuffle(players);
                curPlayerIndex = 0;

                channel.sendMessage("Currently there is no card-trading mechanic.  Good luck!").queue();
            } // ... TODO make everyone trade

            viewTurn();
        } // post-round phase --> playing phase
    }

    /**
     * Takes the amount of Crumbs equal to the buy-in from each player.
     */
    private void takeCrumbs() {
        for (PondscumPlayer pp : players) {
            pool += YeastBot.getProfile(pp.getId()).incrementCrumbs(buyIn);
        }
    }
    /**
     * Add all of the players from the finished players list and the waiting list to the players list.
     */
    private void joinPlayers() {
        int fpsize = finishedPlayers.size();
        for (int i = 0 ; i < fpsize ; i++) {
            players.add(finishedPlayers.remove(0));
        } // in order, add players from the finished players list to the players list.
        int wlsize = waitingList.size();
        for (int i = 0 ; i < wlsize ; i++) {
            players.add(waitingList.remove(0));
        } // in order, add players from the waiting list to the players list
    }
    /**
     * Clean the hands of every player.
     */
    private void cleanPlayers() {
        for (PondscumPlayer pp : players) {
            pp.getHand().clean();
        }
    }
    /**
     * Reset the minimum card value to its original state (one above the highest card value)
     */
    private void resetCardMinValue() {
        curMinValue = (highestCard%13) + 1; // min value is the max value + 1, but loops around
    }


    /**
     * Send a message, pinging the player whose turn it currently is.
     */
    private void viewTurn() {
        if (currentlyPlaying) {
            String output = "";
            output += "```" + getName() + "\n";
            output += "Round discard pile: " + discardPile.toString() + "\n";
            output += "This heat's discard pile: " + heatPile.toString() + "```";
            output += guild.getMemberById(players.get(curPlayerIndex).getId()).getAsMention() + "'s turn - play a card or cards " + curMinValue + " or higher!\n";
            channel.sendMessage(output).queue();
        }
        else
            channel.sendMessage("The game is not currently in play or it is nobody's turn.").queue();
    }
    /**
     *  Send a message with a little bit more detail than viewTurn()
     */
    private void viewGame() {
        String output = "";
        output += "```" + getName() + "\n";
        output += "Turn order: ";
        int playersSize = players.size();
        for (int i = 0 ; i < playersSize ; i++) {
            output += guild.getMemberById(players.get(i).getId()).getNickname() + players.get(i).getWins() + " --> ";
        } // turn order
        output += "\n";
        output += "Buy-in: " + buyIn + "          Pool: " + pool + "\n";
        output += "Round: " + round + "\n";
        if (currentlyPlaying) {
            output += "Round discard pile: " + discardPile.toString() + "\n";
            output += "This heat's discard pile: " + heatPile.toString() + "```" + "\n";
            output += guild.getMemberById(players.get(curPlayerIndex).getId()).getAsMention() + "'s turn - play a card or cards " + curMinValue + " or higher!\n";
        }
        else {
            output += "The game is not currently playing.\n";
        }
        channel.sendMessage(output).queue();
    }
}
