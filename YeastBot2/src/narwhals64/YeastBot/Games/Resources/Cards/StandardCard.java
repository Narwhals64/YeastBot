package narwhals64.YeastBot.Games.Resources.Cards;

import narwhals64.YeastBot.Games.Resources.Card;

public class StandardCard extends Card {
    /**
     * Returns a String representing a card rank based on an int.
     * @param n
     * @return
     */
    public static String getValueFromInt(int n) {
        if (n == 0)
            return "?";
        else if (n == 1 || n == 14)
            return "A";
        else if (n >= 2 && n <= 9)
            return "" + n;
        else if (n == 10)
            return "F";
        else if (n == 11)
            return "J";
        else if (n == 12)
            return "Q";
        else if (n == 13)
            return "K";
        else
            return "ERROR";
    }

    public static String getFullNameFromInt(int n) {
        if (n == 0)
            return "Joker";
        else if (n == 1 || n == 14)
            return "Ace";
        else if (n >= 2 && n <= 9)
            return "" + n;
        else if (n == 10)
            return "10";
        else if (n == 11)
            return "Jack";
        else if (n == 12)
            return "Queen";
        else if (n == 13)
            return "King";
        else
            return "ERROR";
    }


    public StandardCard() {
        super();
    }
    public StandardCard(int rank, int suit) {
        super(rank, suit);
    }

    public int getRank() {
        return getMinorRank();
    }
    public void setRank(int rank) {
        setMinorRank(rank);
    }

    public int getSuit() {
        return getMajorRank();
    }
    public void setSuit(int suit) {
        setMajorRank(suit);
    }

    /**
     * By Standard Card rules, will return a boolean based on if the given String is equivalent to the actual rank of the card.
     * @param s String to compare
     * @return true if they are equal
     */
    public boolean isEqualTo(String s) {
        if (s.equals("" + getMinorRank())) {
            return true;
        }
        if (s.equalsIgnoreCase("F") && getMinorRank() == 10) {
            return true;
        }
        if (s.equalsIgnoreCase("J") && getMinorRank() == 11) {
            return true;
        }
        if (s.equalsIgnoreCase("Q") && getMinorRank() == 12) {
            return true;
        }
        if (s.equalsIgnoreCase("K") && getMinorRank() == 13) {
            return true;
        }
        if (s.equalsIgnoreCase("A") && getMinorRank() == 14) {
            return true;
        }
        if (s.equalsIgnoreCase("A") && getMinorRank() == 1) {
            return true;
        }
        if (s.equalsIgnoreCase("?") && getMinorRank() == 0) {
            return true;
        }
        return false;
    }

    public String toString() {
        if (!isFaceUp())
            return "[==]";

        if (getRank() == 0) {
            if (getSuit() == 1 || getSuit() == 3)
                return "[B?]";
            else
                return "[R?]";
        }

        String output = "[";

        if (getRank() > 1 && getRank() < 10)
            output += getRank();
        else if (getRank() == 10)
            output += "F";
        else if (getRank() == 11)
            output += "J";
        else if (getRank() == 12)
            output += "Q";
        else if (getRank() == 13)
            output += "K";
        else if (getRank() == 1 || getRank() == 14)
            output += "A";
        else
            output += "/";

        if (getSuit() == 1)
            output += "♠";
        else if (getSuit() == 2)
            output += "♦";
        else if (getSuit() == 3)
            output += "♣";
        else if (getSuit() == 4)
            output += "♥";
        else
            output += "/";
        return output + "]";
    }



}
