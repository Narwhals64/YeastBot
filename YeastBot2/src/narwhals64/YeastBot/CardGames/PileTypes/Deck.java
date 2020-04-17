package narwhals64.YeastBot.CardGames.PileTypes;

import narwhals64.YeastBot.CardGames.Cards.StandardCard;

import java.util.ArrayList;

public class Deck {
    private ArrayList<StandardCard> deck = new ArrayList<>();
    public Deck() {
        for (int i = 1 ; i < 14 ; i++)
            for (int j = 1 ; j < 5 ; j++)
                deck.add(new StandardCard(i, "" + j));


    }

    public String toString() {
        String output = "";
        for (StandardCard sc : deck)
            output += sc.toString();

        return output;
    }
}
