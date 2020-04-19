package narwhals64.YeastBot.CardGames.PlayerTypes;

import narwhals64.YeastBot.CardGames.CardPlayer;
import narwhals64.YeastBot.CardGames.PileTypes.Hand;

public class PondscumPlayer extends CardPlayer {

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
