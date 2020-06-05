package narwhals64.YeastBot.CardGames.PileTypes;

import narwhals64.YeastBot.CardGames.Card;
import narwhals64.YeastBot.CardGames.Cards.StandardCard;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A stack of cards in which the top card can be drawn and only the top card can be seen.
 */
public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();
    public Deck() {
        for (int i = 1 ; i < 14 ; i++)
            for (int j = 1 ; j < 5 ; j++)
                deck.add(new StandardCard(i, j));
    }

    /**
     * Places a card on top
     * @param card
     */
    public void topDeck(Card card) {
        deck.add(0, card);
    }

    public int getAmt() {
        return deck.size();
    }

    /**
     * Takes a card from the top of the deck, thus removing it from the deck, and returns it.
     * @return
     */
    public Card drawCard() {
        return deck.remove(0);
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * "Physically" flips the Deck, meaning the card order is now reversed and the cards are facing the opposite direction.
     */
    public void flipDeck() {
        Collections.reverse(deck);
        for (Card c : deck)
            c.flip();
    }

    public String toString() {
        return deck.get(0).toString(); // only shows the top card
    }
}
