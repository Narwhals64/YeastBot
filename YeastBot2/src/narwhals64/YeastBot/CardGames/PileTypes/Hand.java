package narwhals64.YeastBot.CardGames.PileTypes;

import narwhals64.YeastBot.CardGames.Card;

import java.util.ArrayList;

/**
 * A list of Cards in which the player can see every card, face up, and draw any of them (the order does not matter).
 */
public class Hand {
    private ArrayList<Card> hand;

    public Hand() {
        hand = new ArrayList<>();
    }

    public void giveCard(Card card) {
        hand.add(card);
    }

    public void sortHand() {

    }

    public int getSize() {
        return hand.size();
    }

    public Card getCard(int n) {
        return hand.get(n % hand.size());
    }

    public String toString() {
        String output = "```Hand: ";
        for (Card c : hand)
            output += c.toString() + " ";
        return output + "```";
    }
}
