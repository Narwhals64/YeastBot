package narwhals64.YeastBot.CardGames;

import java.util.ArrayList;

public class Pile {
    private ArrayList<Card> pile;

    public Pile() {
        pile = new ArrayList<>();
    }

    /**
     * Simple AddCard method: adds a card to the pile ArrayList without concern for gameplay changes.
     * @param c Card to be added.
     */
    public void addCard(Card c) {
        pile.add(c);
    }
}
