package narwhals64.YeastBot;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class GameInstance {
    public static int GAME_INDEX_COUNTER = 0; // a counter for the amount of games that have been run so far.

    private String name; // A String name of the game.
    private int gameIndex; // The index of the game, i.e. how the game is found and how the scope is decided.

    private boolean open; // if true, the game is still running.  If false, the game is permanently closed.

    public GameInstance(String name) {
        this.name = name;
        GAME_INDEX_COUNTER++;
        gameIndex = GAME_INDEX_COUNTER;
        open = true;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getGameIndex() {
        return gameIndex;
    }

    public boolean isOpen() {
        return open;
    }

    public abstract void addPlayer(GuildMessageReceivedEvent event);


    public void enterCommand(GuildMessageReceivedEvent event) {

    }



    public String toString() {
        return name;
    }

    public String displayCurrentState() {
        return "This game exists.";
    }
}
