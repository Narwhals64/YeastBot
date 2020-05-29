package narwhals64.YeastBot;

import net.dv8tion.jda.api.entities.User;

public abstract class GameInstance {
    private String name;

    public GameInstance(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void addPlayer(User newPlayer);

    public String toString() {
        return name;
    }

    public String displayCurrentState() {
        return "This game exists.";
    }
}
