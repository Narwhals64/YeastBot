package narwhals64.YeastBot.CardGames;

public abstract class Card {
    private int minorRank;
    private int majorRank;
    private boolean portrait;
    private boolean faced; // true = face up

    public Card() {
        minorRank = 0;
        majorRank = 0;
        portrait = true;
        faced = true;
    }
    public Card(int min, int maj) {
        minorRank = min;
        majorRank = maj;
        portrait = true;
        faced = true;
    }

    public int getMinorRank() {
        return minorRank;
    }
    public void setMinorRank(int min) {
        minorRank = min;
    }

    public int getMajorRank() {
        return majorRank;
    }
    public void setMajorRank(int maj) {
        majorRank = maj;
    }

    public boolean isPortrait() {
        return portrait;
    }
    /**
     * Sets from portrait to landscape or from landscape to portrait.
     */
    public void slide() {
        portrait = !portrait;
    }
    public void setPortrait() {
        portrait = true;
    }
    public void setLandscape() {
        portrait = false;
    }

    public boolean isFaceUp() {
        return faced;
    }
    public Card flip() {
        faced = !faced;
        return this;
    }
    public void faceUp() {
        faced = true;
    }
    public void faceDown() {
        faced = false;
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
        if (s.equals("F") && getMinorRank() == 10) {
            return true;
        }
        if (s.equals("J") && getMinorRank() == 11) {
            return true;
        }
        if (s.equals("Q") && getMinorRank() == 12) {
            return true;
        }
        if (s.equals("K") && getMinorRank() == 13) {
            return true;
        }
        if (s.equals("A") && getMinorRank() == 14) {
            return true;
        }
        if (s.equals("A") && getMinorRank() == 1) {
            return true;
        }
        return false;
    }

    public String toString() {
        if (faced)
            return "[" + minorRank + "," + majorRank + "]";
        return "[===]";
    }

}
