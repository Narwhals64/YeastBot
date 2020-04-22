package narwhals64.YeastBot;

public class GamePlayer {
    private String id; // Discord unique ID.

    public GamePlayer() {
        id = "";
    }
    public GamePlayer(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
