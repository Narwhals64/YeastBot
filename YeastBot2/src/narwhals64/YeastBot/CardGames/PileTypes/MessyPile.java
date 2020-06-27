package narwhals64.YeastBot.CardGames.PileTypes;

import narwhals64.YeastBot.CardGames.Pile;

public class MessyPile extends Pile {

    private int visibleGroups; // The amount of card groups that are visible.
    private int groupSizes; // How many cards are in each visible card groups.

    private boolean showDeckCount; // Should the toString() method display how many cards are under the visible cards?

    public MessyPile() {
        super();
        visibleGroups = 3;
        groupSizes = 1;
        showDeckCount = true;
    }


    public void setVisibleGroups(int n) {
        visibleGroups = n;
    }

    public void setGroupSizes(int n) {
        groupSizes = n;
    }



    /**
     * A display of the messy pile, showing groupings of cards that should be displayed.
     * @return
     */
    public String toString() {
        int counter = Integer.min(getSize(), visibleGroups * groupSizes);

        String output = "There are " + counter + " visible cards in the pile...\n";

        for (int i = 0 ; i < visibleGroups ; i++) { // goes through each group
            for (int j = 0 ; j < groupSizes ; j++) { // goes through the card in each group
                if (i * groupSizes + j < getSize()) // if the card exists, ...
                    output += getCard(i * groupSizes + j); // ... add the String output of the card
            }
            if (i != visibleGroups-1)
                output += "\n"; // if not the last group, then start a new line as well after the end of the group
        }


        if (showDeckCount && getSize() > counter) // if there are more cards underneath then show how many there are.
            output += "\n...with " + (getSize() - counter) + " more cards underneath.";


        return output;

    }
}
