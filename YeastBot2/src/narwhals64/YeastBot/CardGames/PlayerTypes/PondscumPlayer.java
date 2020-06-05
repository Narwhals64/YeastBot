package narwhals64.YeastBot.CardGames.PlayerTypes;

import narwhals64.YeastBot.CardGames.PileTypes.Hand;
import narwhals64.YeastBot.CardGames.GamePlayer;

/**
 * A player in a game of Pondscum.  Attached to one user id.
 */
public class PondscumPlayer extends GamePlayer {

    private Hand hand;

    public PondscumPlayer() {
        super();
        hand = new Hand();
    }
    public PondscumPlayer(String id) {
        super(id);
        hand = new Hand();
    }


    public Hand getHand() {
        return hand;
    }


}
