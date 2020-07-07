package narwhals64.YeastBot.Games.Resources.PileTypes;

import narwhals64.YeastBot.Games.Resources.Cards.StandardCard;
import narwhals64.YeastBot.Games.Resources.Pile;

/**
 * A stack of cards in which the top card can be drawn and only the top card can be seen.
 */
public class Deck extends Pile {
    public Deck() {
        for (int i = 1 ; i < 14 ; i++)
            for (int j = 1 ; j < 5 ; j++)
                topDeck(new StandardCard(i, j));
        flipPile(); // decks should be face down by default
    } // Default constructor for StandardCards


    public String toString() {
        if (getSize() != 0)
            return get(0).toString(); // only shows the top card
        return "[XX]";
    }
}
