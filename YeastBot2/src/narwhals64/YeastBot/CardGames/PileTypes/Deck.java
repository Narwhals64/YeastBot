package narwhals64.YeastBot.CardGames.PileTypes;

import narwhals64.YeastBot.CardGames.Card;
import narwhals64.YeastBot.CardGames.Cards.StandardCard;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck = new ArrayList<>();
    public Deck() {
        for (int i = 1 ; i < 14 ; i++)
            for (int j = 1 ; j < 5 ; j++)
                deck.add(new StandardCard(i, j));
    }

    public void shuffle() {
        Collections.shuffle(deck);
    }

    /**
     * "Physically" flips the Deck, meaning the card order is now reversed.
     */
    public void flipDeck() {
        Collections.reverse(deck);
        for (Card c : deck)
            c.flip();
    }

    public String toString() {
        String output = "```";
        for (Card c : deck)
            output += c.toString();

        return output + "```";
    }
}
