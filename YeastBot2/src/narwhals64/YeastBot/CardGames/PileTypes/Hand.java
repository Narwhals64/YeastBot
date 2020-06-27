package narwhals64.YeastBot.CardGames.PileTypes;

import narwhals64.YeastBot.CardGames.Card;
import narwhals64.YeastBot.CardGames.Pile;

import java.util.ArrayList;

/**
 * A list of Cards in which the player can see every card, face up, and draw any of them (the order does not matter).
 */
public class Hand extends Pile {

    public Hand() {
        super();
    }

    /**
     * Rephrasing of addCard(card)
     * @param card
     */
    public void giveCard(Card card) {
        addCard(card);
    }

    /**
     * Removes a card from the Hand with the given rank, then returns it.
     * @param rank String that describes a rank.
     * @return The Card that was removed.
     */
    public Card takeCard(String rank) {
        int size = getSize();
        for (int i = 0 ; i < size ; i++) {
            if (get(i).isEqualTo(rank)) {
                return remove(i);
            }
        }
        return null;
    }

    public boolean canReveal(String s) {
        for (int i = 0 ; i < getSize() ; i++) {
            if (getCard(i).isEqualTo(s))
                return true;
        }
        return false;
    }

    public void sortHand() {

    }

    public String toString() {
        String output = "Hand: ";
        int size = getSize();
        for (int i = 0 ; i < size ; i++) {
            output += get(i).toString() + " ";
        } // add each card to the output.
        return output;
    }
}
