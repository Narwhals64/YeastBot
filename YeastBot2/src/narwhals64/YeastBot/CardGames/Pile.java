package narwhals64.YeastBot.CardGames;

import java.util.ArrayList;
import java.util.Collections;

public class Pile {
    private ArrayList<Card> pile;

    public Pile() {
        pile = new ArrayList<>();
    }


    /**
     * Get the card at index n.
     * @param n index of card to return
     * @return a Card.
     */
    public Card get(int n) {
        return pile.get(n);
    }
    public Card getCard(int n) {
        return pile.get(n);
    }
    public Card removeBottomCard() {
        return pile.remove(pile.size() - 1);
    }
    public Card getBottomCard() {
        return pile.get(pile.size() - 1);
    }

    /**
     * Adds a card to the "top" of the pile.
     * This would be index 0 of the ArrayList.
     * @param c
     */
    public void topDeck(Card c) {
        pile.add(0,c);
    }

    public Pile putOnto(Pile other) {
        while (getSize() != 0)
            other.topDeck(removeBottomCard());
        return other;
    }

    /**
     * Remove the top card (index 0)
     * @return The removed Card
     */
    public Card draw() {
        return remove(0);
    }

    /**
     * Removes a card at index n.
     * @param n index of card to be removed.
     * @return The Card that was removed.
     */
    public Card remove(int n) {
        return pile.remove(n);
    }

    /**
     * Removes all cards.
     * @return this object.
     */
    public Pile clean() {
        pile.clear();
        return this;
    }

    /**
     * Computer-generated shuffling algorithm.
     */
    public void shuffle() {
        Collections.shuffle(pile);
    }

    /**
     * "Physically" flips the Deck, meaning the card order is now reversed and the cards are facing the opposite direction.
     */
    public void flipPile() {
        Collections.reverse(pile);
        for (Card c : pile)
            c.flip();
    }

    /**
     * Return the size
     * @return an int of the amount of cards in the pile
     */
    public int getSize() {
        return pile.size();
    }

    /**
     * Are all cards the same rank or a Joker?
     */
    public boolean allCardsSimilarRank() {
        int localRank = -1;
        for (Card c : pile) {
            if (c.getMinorRank() == 0)
                ; // nothing
            else if (localRank == -1)
                localRank = c.getMinorRank();
            else if (c.getMinorRank() != localRank)
                return false;
        }
        return true;
    }

    /**
     * Simple AddCard method: adds a card to the pile ArrayList without concern for gameplay changes.
     * @param c Card to be added.
     */
    public void addCard(Card c) {
        pile.add(c);
    }

    public String toString() {
        return "YeastBot.CardGames.Pile.java does not have a toString() method.";
    }
}
