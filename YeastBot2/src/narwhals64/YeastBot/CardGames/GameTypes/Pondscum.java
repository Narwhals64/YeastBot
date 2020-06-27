package narwhals64.YeastBot.CardGames.GameTypes;

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
    private ArrayList<PondscumPlayer> finishedPlayers; // when a player is finished with his hand, he goes here.
    private ArrayList<PondscumPlayer> players; // at first, this is not ordered.  After the first game, it is ordered.  First is prez, last is pondscum
    private ArrayList<PondscumPlayer> waitingList; // waiting list for new players.  Each new player pushes the rest of the players up, thus becoming the next Pondscum.
    private boolean allPlayersNormal; // for the first game, all players are considered normal.

    private MessyPile heatPile;
    private Deck discard;

    private Guild guild;
    private TextChannel channel;

    private int highestCard; // card with the highest value.  Default is 3.  Everything below it, looping around from 2-Ace-King, is "lesser" than the highest card value
    private boolean currentlyPlaying;
    private int curPlayerIndex;
    private int cardMinValue; // the current minimum value for a card to be played.  Played cards must be this value or higher.
    private int cardAmtNeeded; // the amount of cards needed to play in this heat.
    private int firstPass;

    public Pondscum(GuildMessageReceivedEvent event) {
        super("Pondscum Game #" + (GAME_INDEX_COUNTER+1));

        host = new PondscumPlayer(event.getAuthor().getId());
        finishedPlayers = new ArrayList<>();
        players = new ArrayList<>();
        waitingList = new ArrayList<>();
        waitingList.add(host);
        allPlayersNormal = true;

        heatPile = new MessyPile();
        discard = new Deck();

        guild = event.getGuild();
        channel = event.getChannel();

        highestCard = 3;
        currentlyPlaying = false;
        curPlayerIndex = 0;
        cardMinValue = 0;
        cardAmtNeeded = 0;
        firstPass = -1;
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
     * Enter a command into the game.  The brains of the operation.
     * @param event The event of the message being sent.
     */
    public void enterCommand(GuildMessageReceivedEvent event) {
        String com = event.getMessage().getContentRaw();

        if (com.equalsIgnoreCase("start") && event.getAuthor().getId().equals(host.getId())) { // if host entered start
            if (!currentlyPlaying)
                initializeRound();
            else {
                channel.sendMessage("The game is already in session.").queue();
            }
        } // "start" --> Start the game.
        else if (com.equalsIgnoreCase("hand") || com.equalsIgnoreCase("h")) {
            event.getAuthor().openPrivateChannel().queue((channel) -> {
                channel.sendMessage("```" + getPlayer(event.getAuthor()).getHand().toString() + "```").queue();
            }); // Send a message displaying the player's hand.
        } // "h" --> Whisper your hand to you.
        else if (com.equalsIgnoreCase("turn") || com.equalsIgnoreCase("t")) {
            announcePlayerTurn();
        } // "t" --> Show whose turn it is.
        else if ((com.equalsIgnoreCase("pass") || com.equalsIgnoreCase("p")) && event.getAuthor().getId().equals(players.get(curPlayerIndex).getId())) {
            if (firstPass == -1) { // if nobody has passed yet, then this pass is the first player in the streak to pass.
                firstPass = curPlayerIndex;
            }
            curPlayerIndex = (curPlayerIndex + 1)%players.size(); // do not play any cards and have the next person take their turn
            if (firstPass == curPlayerIndex) { // now, if it's the turn of the first person who started the passing streak, reset the minimum card value
                resetCardMinValue(); // these are the same instructions as the "heat end" instructions for when cards are actually played.
                cardAmtNeeded = 0;
                heatPile.putOnto(discard);
            }
            announcePlayerTurn();
        } // "p" --> pass
        else if (event.getAuthor().getId().equals(players.get(curPlayerIndex).getId())){ // consider anything else to be playing cards as long as it's the right player.
            String[] wantToPlay = com.split("\\s+"); // separate the command by spaces.

            PondscumPlayer player = getPlayer(event.getAuthor());
            Pile cardsToBePlayed = new Pile();


            boolean canBePlayed = true;

            for (String s : wantToPlay) {
                if (player.getHand().canReveal(s))
                    cardsToBePlayed.addCard(player.getHand().takeCard(s));
                else
                    canBePlayed = false;
            } // make a pile out of the cards you are attempting to play.
            // Test to see if the pile can be played
            int cardAmtPlayed = cardsToBePlayed.getSize();

            for (int i = 0 ; i < cardAmtPlayed ; i++) {
                if (cardsToBePlayed.get(i) == null)
                    canBePlayed = false;
            } // check if any of the cards are null.  If they are null then the pile cannot be played.
            if (!cardsToBePlayed.allCardsSimilarRank()) {
                canBePlayed = false;
            } // check if the cards are of similar rank.  If they are not then they cannot be played.
            if (cardAmtNeeded != 0 && cardAmtPlayed != cardAmtNeeded) { // if there is an amount of cards required for the round and that amount is not met
                canBePlayed = false;
            } // check if the amount of cards is viable
            if (cardAmtPlayed < 1 || cardAmtPlayed > 4) {
                canBePlayed = false;
            } // check if the amount of cards is between one and four

            // Now find the value that is being played.
            int valuePlayed = highestCard; // Assume highest card.  Thus, if only jokers are played, they will be seen as highest card.
            for (int i = 0 ; i < cardAmtPlayed ; i++) { // go through each card
                if (cardsToBePlayed.get(i).getMinorRank() != 0) // skip the card if it is a joker
                    valuePlayed = cardsToBePlayed.get(i).getMinorRank(); // set the value equal to the card
            } // Find the played pile's value (all cards will be the same or jokers)

            if (!checkAcceptableValue(valuePlayed)) {
                canBePlayed = false;
            } // check if the value played is acceptable


            if (canBePlayed) {
                firstPass = -1; // if someone played, then end the passing streak.
                cardAmtNeeded = cardAmtPlayed; // if this was 0 before, then it has an actual amount now
                heatPile.setGroupSizes(cardAmtNeeded);

                while (cardsToBePlayed.getSize() > 0) {
                    heatPile.topDeck(cardsToBePlayed.remove(0));
                } // place the cards being played into the heat discard
                if (valuePlayed == highestCard) { // if this is true, the "heat" is over.
                    resetCardMinValue();
                    cardAmtNeeded = 0;
                    heatPile.putOnto(discard);
                } // either end the heat and start another heat with the person who just played ...
                else {
                    cardMinValue = (valuePlayed%13) + 1;
                    curPlayerIndex = (curPlayerIndex + 1)%players.size();
                } // ... or don't do that

                checkWinner(); // either way, check to see if the person who played won
                announcePlayerTurn();
            } // if the cards can be played, then play them and proceed to the next player's turn.
            else {
                while (cardsToBePlayed.getSize() != 0)
                    player.getHand().giveCard(cardsToBePlayed.remove(0));
                channel.sendMessage("Sorry, but those cards could not be played.").queue();
            } // if the cards cannot be played, then return them to the player's hand.



        } // Anything else --> Play cards (if cur player).
    }

    /**
     * Start the round, as in set it up for play.  One round refers to the playing of the cards until a new roster of pres, VP, etc. is created.
     */
    private void initializeRound() {

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
            heatPile.setVisibleGroups(3);
            heatPile.setGroupSizes(1);
        } // fourth, reset all variables to begin the game.


        if (allPlayersNormal) { // if this is the first round then everyone is a normal and does not have to trade cards.
            Collections.shuffle(players);
            //allPlayersNormal = false; TODO
        } // fifth, either start the game without trading (if it's the first round) or ...
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
        announcePlayerTurn();


    }
    /**
     * Insert all players in the waiting list into the player list.
     * Each player is moved to the bottom of the player list, making them automatically Pondscum.
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
     * Reset the minimum card value to its original state
     */
    private void resetCardMinValue() {
        cardMinValue = (highestCard%13) + 1; // min value is the max value + 1, but loops around
    }


    /**
     * Send a message, pinging the player whose turn it currently is.
     */
    private void announcePlayerTurn() {
        if (currentlyPlaying) {
            String output = "";
            output += "```" + getName() + "\n";
            output += "Round discard pile: " + discard.toString() + "\n";
            output += "This heat's discard pile: " + heatPile.toString() + "```";
            output += guild.getMemberById(players.get(curPlayerIndex).getId()).getAsMention() + "'s turn - play a card " + getMinCardValueName() + " or higher!\n";
            channel.sendMessage(output).queue();
        }
        else
            channel.sendMessage("The game is not currently in play or it is nobody's turn.").queue();
    }
    /**
     * Find the name of the value of the minimum card required to play
     * @return String name of the card rank
     */
    private String getMinCardValueName() {
        return StandardCard.getFullNameFromInt(cardMinValue);
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
     * Test a value to see if it's acceptable via the rules of Pondscum
     * @param n Value to be tested.
     * @return true if the given value is "greater" than the current required value, i.e. acceptable.
     */
    private boolean checkAcceptableValue(int n) {
        int min = cardMinValue;
        int max = highestCard;
        if (min == max) // if the only card that can be played is the highest card, check if the played card is the highest card.
            return n == min;
        else if (min > max) // if min is greater than max, then the number line "loops," meaning only one condition has to be true.
            return (n >= min || n <= max);
        else // otherwise, calculate normally.
            return (n >= min && n <= max);

    }

    /**
     * Checks to see if there are any winners.
     */
    private void checkWinner() {
        for (int i = 0 ; i < players.size() ; i++) { // cycle through the players list
            if (players.get(i).getHand().getSize() == 0) { // if a player's hand is empty
                channel.sendMessage(guild.getMemberById(players.get((curPlayerIndex - 1 + players.size()) % players.size()).getId()).getAsMention() + "has finished!").queue();
                finishedPlayers.add(players.remove(i)); // then remove them from the list and add them to the finished players list
                i--; // also, subtract one from i to make it cycle through every player
            }
        }
        if (players.size() == 1) {
            finishedPlayers.add(players.remove(0));
            currentlyPlaying = false;
            channel.sendMessage("The game is now over!  Enter \"start\" to begin a new round!");
        }
    }


    /**
     * For the ,view command, this displays the current state of the game.
     * @return String displaying the current state
     */
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
