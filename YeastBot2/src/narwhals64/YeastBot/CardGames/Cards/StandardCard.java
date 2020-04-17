package narwhals64.YeastBot.CardGames.Cards;

import narwhals64.YeastBot.CardGames.Card;

public class StandardCard extends Card {
    private int rank;
    private String suit;


    public StandardCard() {
        rank = 0;
        suit = "";
    }

    public StandardCard(int rank, String suit) {
        this.rank = rank;
        this.suit = suit;
        if (suit.equals("S") || suit.equals("1"))
            this.suit = "Spades";
        else if (suit.equals("D") || suit.equals("2"))
            this.suit = "Diamonds";
        else if (suit.equals("C") || suit.equals("3"))
            this.suit = "Clubs";
        else if (suit.equals("H") || suit.equals("4"))
            this.suit = "Hearts";
    }

    public String toString() {
        if (rank == 0) { // test for Joker
            if (suit.equals("Spades") || suit.equals("Clubs"))
                return "[B?]";
            else
                return "[R?]";
        }

        String output = "[";

        if (rank > 1 && rank < 10)
            output += rank;
        else if (rank == 10)
            output += "0";
        else if (rank == 11)
            output += "J";
        else if (rank == 12)
            output += "Q";
        else if (rank == 13)
            output += "K";
        else if (rank == 14 || rank == 1)
            output += "A";

        if (suit.equals("Spades"))
            output += "S]";
        else if (suit.equals("Diamonds"))
            output += "D]";
        else if (suit.equals("Clubs"))
            output += "C]";
        else
            output += "H]";

        return output;
    }

}
