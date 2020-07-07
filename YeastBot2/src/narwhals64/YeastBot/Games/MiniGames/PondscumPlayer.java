package narwhals64.YeastBot.Games.MiniGames;

import narwhals64.YeastBot.Games.Resources.PileTypes.Hand;
import narwhals64.YeastBot.Games.Resources.GamePlayer;

/**
 * A player in a game of Pondscum.  Attached to one user id.
 */
public class PondscumPlayer extends GamePlayer {

    private Hand hand;
    private int wins;

    public PondscumPlayer() {
        super();
        hand = new Hand();
    }
    public PondscumPlayer(String id) {
        super(id);
        hand = new Hand();
    }

    public int addWins(int n) {
        wins += n;
        return wins;
    }
    public int getWins() {
        return wins;
    }


    public Hand getHand() {
        return hand;
    }


}
