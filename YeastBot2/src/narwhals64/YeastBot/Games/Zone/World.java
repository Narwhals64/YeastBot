package narwhals64.YeastBot.Games.Zone;

import narwhals64.YeastBot.YeastBot;

import java.io.BufferedWriter;
import java.io.FileWriter;

public class World {
    public static String worldsPath = "C:\\Users\\Ethan Rao\\Desktop\\Yeast Bot\\Zone\\worlds";

    private String name; // Name of the world, i.e. the planet
    private String id; // id of the world, i.e. the index of the world
    private int x; // x coordinate of the world to appear on the galaxy
    private int y; // y coordinate of the world to appear on the galaxy

    private String seed;


    public World() {
        name = "Unnamed World";
        id = "" + Galaxy.worlds.size();
        Galaxy.worlds.add(this);
        x = 0;
        y = 0;

        seed = "";
    }


    public void save() {
        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(worldsPath + id + ".txt"));

            bw.write(name + ":" + x + ":" + y);
            bw.newLine();
            bw.write(seed);

            bw.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
