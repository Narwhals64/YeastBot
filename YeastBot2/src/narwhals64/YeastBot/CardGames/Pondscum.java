package narwhals64.YeastBot.CardGames;

import narwhals64.YeastBot.CardGames.PlayerTypes.PondscumPlayer;

import java.util.ArrayList;

public class Pondscum {
    private ArrayList<PondscumPlayer> players; // at first, this is not ordered.  After the first game, it is ordered.
    private ArrayList<PondscumPlayer> waitingList; // waiting list for new players.  Each new player pushes the rest of the players up, thus becoming the next Pondscum.
    private boolean allPlayersNormal; // for the first game, all players are considered normal.

    public Pondscum() {
        players = new ArrayList<>();
        waitingList = new ArrayList<>();
        allPlayersNormal = true;
    }

    /**
     * Add a player to the waiting list.
     * @param newPlayer PondscumPlayer to be added.
     */
    public void addPlayer(PondscumPlayer newPlayer) {
        waitingList.add(newPlayer);
    }

    /**
     * Insert all players in the waiting list into the player list.
     * Each player is moved to the bottom of the player list, making them automatically Pondscum.
     */
    public void joinPlayers() {
        int wlsize = waitingList.size();
        for (int i = 0 ; i < wlsize ; i++) {
            players.add(0,waitingList.get(0)); // Move the first person from the waiting list to the bottom of the players list, thus making them pondscum.
            waitingList.remove(0); // now remove the player from the waitingList since they are in the game.
        }
    }



}
