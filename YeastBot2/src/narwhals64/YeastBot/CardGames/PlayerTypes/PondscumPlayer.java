package narwhals64.YeastBot.CardGames.PlayerTypes;

import narwhals64.YeastBot.CardGames.PileTypes.Hand;
import narwhals64.YeastBot.GamePlayer;

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


}
