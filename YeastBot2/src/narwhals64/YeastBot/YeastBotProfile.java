package narwhals64.YeastBot;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.stream.Stream;

import narwhals64.YeastBot.Items.Item;
import narwhals64.YeastBot.Items.Containers.Inventory;
import narwhals64.YeastBot.Items.MasonJarOfYeastBotsShum;
import narwhals64.YeastBot.Items.Tag;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.annotation.Nullable;

public class YeastBotProfile {

	private String id;

	// Line 1 - Permissions
	private int administration;

	// Line 2 - General Statistics


	// Line 3 - Currencies
	private int crumbs; // this is the basic currency of Yeast Bot
	private int loaves; // this is the more elusive currency of Yeast Bot

	// Line 4 - Inventory
	private Inventory inv;




	/**
	 * Create a YeastBotProfile class
	 * @param str Discord id
	 */
	public YeastBotProfile(String str) {
		administration = 0;

		id = str;

		crumbs = 0;
		loaves = 0;

		inv = new Inventory();
		inv.setOwner(this);


		load();
	}

	/**
	 * Get the Discord ID of the profile
	 * @return String of Discord ID
	 */
	public String getId() {
		return id;
	}

	public int getAdministration() {
		return administration;
	}

	/**
	 * Get the amount of Crumbs this profile has
	 * @return int of Crumb amount
	 */
	public int getCrumbs() {
		return crumbs;
	}
	/**
	 * Set the amount of Crumbs this profile has.
	 * @param n int of the amount of Crumbs to be set.
	 */
	public void setCrumbs(int n) {
		crumbs = n;
	}
	/**
	 * Increment the Crumbs this profile has by an amount.
	 * @param n int amount to be incremented.
	 */
	public int incrementCrumbs(int n) {
		crumbs += n;
		return n;
	}

	public int getLoaves() {
		return loaves;
	}
	public void setLoaves(int n) {
		loaves = n;
	}
	public void incrementLoaves(int n) {
		loaves += n;
	}

	/**
	 * Get the Inventory item tied to this profile.
	 * @return Inventory (extends ItemDirectory (extends Item)) inv
	 */
	public Inventory getInv() {
		return inv;
	}

	/**
	 * Find the first item that matches the given id within the Inventory.
	 * Does not search other containers within Inventory.
	 * @param id int ID of Item
	 * @return Item with given ID or null
	 */
	public Item findItem(int id) {
		return inv.findItem(id);
	}
	public Item getItem(int n) {
		return inv.findItem(n);
	}

	public void addItem(Item item) {
		inv.addItem(item.setOwner(this));
	}
	public void addItem(int n) {
		inv.addItem(Item.fetchItem(n).setOwner(this));
	}

	/**
	 * Loads the profile's data from its matching text file.
	 * Overrides any current values.
	 */
	public void load() {
		try {

			BufferedReader br = new BufferedReader(new FileReader(new File(YeastBot.profilesPath + id + ".txt")));

			// PERMISSIONS
			administration = Integer.parseInt(br.readLine());

			// GENERAL STATS
			br.readLine();

			// CURRENCIES
			String[] currencies = br.readLine().split(":");

			setCrumbs(Integer.parseInt(currencies[0]));
			setLoaves(Integer.parseInt(currencies[1]));

			// INVENTORY
			String rawItemData = br.readLine();

			inv.setContainmentData(rawItemData);


			br.close();


		} catch (Exception e) {

			try {

				BufferedWriter bw = new BufferedWriter(new FileWriter(YeastBot.profilesPath + id + ".txt"));

				bw.write("0"); // No permissions
				bw.newLine();
				bw.write(""); // No general stats
				bw.newLine();
				bw.write("1000:0"); // 1000 crumbs; 0 loaves
				bw.newLine();
				bw.write("1{1}[]:110{3}"); // tag bag and three three-letter gachas

				bw.close();

				load(); // call back to the load method.  If this catch was used, then a text file did not exist.  Now that it exists, this catch will not be used again.

			} catch (Exception f) {
				System.out.println("Something went wrong trying to load a profile.");
			}

		}
	}

	/**
	 * Given a String, this method will separate the input into an array of Strings.
	 * Each element within the input will be of the form: itemNumber{itemValues}[containmentValues]
	 * Example: 1{1:2:1:1}[1:4:5{2:3}:7]
	 *
	 * @param input
	 * @return
	 */
	public static String[] separateWithParams(String input) {
		ArrayList<String> outputAL = new ArrayList<>();

		char separator = ':';

		String entry = "";

		int length = input.length();

		int curly = 0;
		int square = 0;
		int parenth = 0;

		for (int i = 0 ; i < length ; i++) {

			char curChar = input.charAt(i);

			if (curChar == '{')
				curly++;
			else if (curChar == '}')
				curly--;
			else if (curChar == '[')
				square++;
			else if (curChar == ']')
				square--;
			else if (curChar == '(')
				parenth++;
			else if (curChar == ')')
				parenth--;



			if (i == length - 1) {
				entry += curChar;
				outputAL.add("" + entry);
				entry = "";
			}
			else if (curly == 0 && square == 0 && parenth == 0 && curChar == separator) {
				outputAL.add("" + entry);
				entry = "";
			}
			else
				entry += curChar;


		}

		int size = outputAL.size();

		String[] output = new String[size];

		for (int i = 0 ; i < size ; i++) {
			output[i] = outputAL.get(i);
		}

		return output;

	}

	/**
	 * Cleans the Inventory, removing any Items with an amount of 0 or less.
	 */
	public void clean() {
        inv.clean();
    }

	/**
	 * Saves the profile to its matching text file.
	 */
	public void save() {
		try {

			BufferedWriter bw = new BufferedWriter(new FileWriter(YeastBot.profilesPath + id + ".txt"));

			bw.write("" + administration);
			bw.newLine();
			bw.write("");
			bw.newLine();
			bw.write(crumbs + ":" + loaves);
			bw.newLine();
			bw.write(inv.getSaveData());

			bw.close();


		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void view(GuildMessageReceivedEvent event, int level) {
		inv.view(event,level);
	}
	public void view(GuildMessageReceivedEvent event) {
		inv.view(event,0);
	}

	public void incrementGrains(int i) {

	}


}
