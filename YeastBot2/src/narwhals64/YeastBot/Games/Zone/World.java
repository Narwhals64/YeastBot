package narwhals64.YeastBot.Games.Zone;

import narwhals64.YeastBot.YeastBot;

import java.io.BufferedWriter;
import java.io.FileWriter;

/**
 * The World object acts both a
 */
public class World {
    public static String worldsPath = "C:\\Users\\Ethan Rao\\Desktop\\Yeast Bot\\Zone\\worlds";

    private String name; // Name of the world, i.e. the planet
    private String id; // id of the world, i.e. the index of the world
    private int x; // x coordinate of the world to appear on the galaxy
    private int y; // y coordinate of the world to appear on the galaxy

    private int seedVer; // version of seed
    private String seed; // seed for the world, how the terrain was randomized.



    public World() {
        name = "Unnamed World";
        id = "" + Galaxy.worlds.size();
        Galaxy.worlds.add(this);
        x = 0;
        y = 0;

        seed = "";
    }

    public void setName(String str) {
        name = str;
    }
    public String getName() {
        return name;
    }

    public void setId(String str) {
        id = str;
    }
    public String getId() {
        return id;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public int getSeedVer() {
        return seedVer;
    }
    public void setSeedVer(int n) {
        seedVer = n;
    }

    public String getSeed() {
        return seed;
    }
    public void setSeed(String str) {
        seed = str;
    }


    /**
     * World Name : X Position : Y Position
     * Seed Version : Seed
     * Binary digit representing presence of expansion (0 or 1) : etc : etc : etc . . .
     * Player 0's Discord ID : Player 1's Discord ID : etc : etc : etc . . .
     * Event(event data) : Event(event data) : etc : etc : etc . . .
     *
     * -- Example:
     * Icelonia:345:429
     * 1:98237598273
     * 0:1:0:0
     * 240601741085769729:610132976260481056
     * 0(0:46:29):0(1:40:35):1(8)
     */
    public void save() {
        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(worldsPath + id + ".txt"));

            bw.write(name + ":" + x + ":" + y);
            bw.newLine();
            bw.write(seedVer + ":" + seed);

            bw.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
