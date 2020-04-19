package narwhals64.YeastBot.CardGames.PileTypes;

import narwhals64.YeastBot.CardGames.Card;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> hand;

    public Hand() {
        hand = new ArrayList<>();
    }

    public Card getCard(int n) {
        return hand.get(n % hand.size());
    }
}
