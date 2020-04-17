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

        if (getRank() > 1 || getRank() < 10)
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
            output += "S";
        else if (getSuit() == 2)
            output += "D";
        else if (getSuit() == 3)
            output += "C";
        else if (getSuit() == 4)
            output += "H";
        else
            output += "/";

        return output;
    }

}
