package narwhals64.YeastBot.CardGames.Cards;

import narwhals64.YeastBot.CardGames.Card;

public class StandardCard extends Card {

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
     * Test a String to see if the card is representative of the String.
     * Example: "3" is representative of a three of spades.
     * Example: "F" is representative of a 10 of hearts, as is "10".
     * Example: "1" is representative of an ace of spades, as is "1" and "A".
     * @param str String to be tested.
     * @return Whether it is representative or not.
     */
    public boolean isEqualTo(String str) {
        if (str.equals("F") && getRank() == 10) {
            return true;
        } // ten
        else if (str.equals("J") && getRank() == 11) {
            return true;
        } // jack
        else if (str.equals("Q") && getRank() == 12) {
            return true;
        } // queen
        else if (str.equals("K") && getRank() == 13) {
            return true;
        } // king
        else if ( ( str.equals("A")||str.equals("1") ) && ( getRank() == 1||getRank() == 14 ) ) {
            return true;
        } // ace
        else {
            try {
                int attempt = Integer.parseInt(str);
                if (getRank() == attempt) {
                    return true;
                }
            }
            catch (Exception e) {
                return false;
            }
        } // 2-9
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
