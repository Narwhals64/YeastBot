package narwhals64.YeastBot.Games.Resources.PileTypes;

import narwhals64.YeastBot.Games.Resources.Card;
import narwhals64.YeastBot.Games.Resources.Pile;

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

    /**
     * Sorts the hand by minor rank, then by major rank, with n as the lowest card
     * @param n
     */
    public Hand sortHand(int n) {
        Hand sortedHand = mergeSortHand(this,n);

        int sortedHandSize = sortedHand.getSize();
        for (int i = 0 ; i < sortedHandSize ; i++) {
            giveCard(sortedHand.remove(0));
        }

        return this;
    }
    private Hand mergeSortHand(Hand hand, int n) {
        if (hand.getSize() <= 1)
            return hand;

        Hand a = new Hand();
        Hand b = new Hand();

        int size = hand.getSize();
        int mid = size / 2;

        for (int i = 0 ; i < mid ; i++) {
            a.giveCard(hand.remove(0));
        }
        for (int i = mid ; i < size ; i++) {
            b.giveCard(hand.remove(0));
        }

        return mergeSortedHands(mergeSortHand(a,n),mergeSortHand(b,n),n);

    }
    private Hand mergeSortedHands(Hand a, Hand b, int n) {
        if (a.getSize() == 0) {
            return b;
        }
        else if (b.getSize() == 0) {
            return a;
        }


        Hand output = new Hand();

        while (a.getSize() != 0 || b.getSize() != 0) {


            if (a.getSize() == 0) {
                output.giveCard(b.remove(0));
            } // if a is depleted, give b
            else if (b.getSize() == 0) {
                output.giveCard(a.remove(0));
            } // if b is depleted, give a
            else {
                int an = a.getCard(0).getMinorRank();
                int bn = b.getCard(0).getMinorRank();

                if (an == 0) {
                    output.giveCard(a.remove(0));
                } // if a is joker, give a
                else if (bn == 0) {
                    output.giveCard(b.remove(0));
                } // if b is joker, give b
                else if (((an - n + 13) % 13) < ((bn - n + 13) % 13)) {
                    output.giveCard(a.remove(0));
                } // a's card is before b
                else if (((an - n + 13) % 13) > ((bn - n + 13) % 13)) {
                    output.giveCard(b.remove(0));
                } // b's card is before a
                else {

                    if (a.getCard(0).getMajorRank() < b.getCard(0).getMajorRank()) // a wins - give a
                        output.giveCard(a.remove(0));
                    else // b wins or they tie - give b
                        output.giveCard(b.remove(0));

                } // a's card and b's card tie for minor rank - move onto major rank

            } // if neither are depleted, we can find the values of a. and b.getCard(0)
        } // loop through and sort

        return output;

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
