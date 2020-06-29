package narwhals64.YeastBot;

public class GuildProfile {

    private String id;

    private String prefix;

    public GuildProfile(String id) {
        this.id = id;

        prefix = ",";
    }

    public String getId() {
        return id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String newPrefix) {
        prefix = newPrefix;
    }


}
