package narwhals64.YeastBot.CardGames.PileTypes;

import narwhals64.YeastBot.CardGames.Card;
import narwhals64.YeastBot.CardGames.Cards.StandardCard;
import narwhals64.YeastBot.CardGames.Pile;

import java.util.ArrayList;
import java.util.Collections;

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
        return get(0).toString(); // only shows the top card
    }
}
