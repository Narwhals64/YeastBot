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

public class YeastBotProfile {

	private String id;

	private int crumbs; // this is the basic currency of Yeast Bot
	private int loaves;

	private Inventory inv;


	public YeastBotProfile(String str) {
		id = str;

		crumbs = 0;
		loaves = 0;

		inv = new Inventory();


		load();
	}


	public String getId() {
		return id;
	}

	public int getCrumbs() {
		return crumbs;
	}
	public void setCrumbs(int n) {
		crumbs = n;
	}
	public void incrementCrumbs(int n) {
		crumbs += n;
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


	public Inventory getInv() {
		return inv;
	}
	public Item getItem(int n) {
		return inv.findItem(n);
	}

	public void addItem(Item item) {
		inv.addItem(item);
	}
	public void addItem(int n) {
		inv.addItem(Item.fetchItem(n));
	}

	public void load() {
		try {

			BufferedReader br = new BufferedReader(new FileReader(new File(YeastBot.profilesPath + id + ".txt")));


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

				bw.write("1000:0"); // 1000 crumbs; 0 loaves
				bw.newLine();
				bw.write("2[]:12"); // tag bag and one three letter gacha

				bw.close();

				load();

			} catch (Exception f) {
				System.out.println("Something went wrong trying to load a profile.");
			}

		}
	}

	/**
	 * Given a String, this method will separate the input into an array of Strings.
	 * Each element within the input will be of the form: itemNumber{itemValues}[containmentValues]
	 * Example: 1{1,2,1,1}[1,4,5{2,3},7]
	 *
	 * @param input
	 * @return
	 */
	public static String[] separateWithParams(String input) {
		ArrayList<String> outputAL = new ArrayList<>(); // create ArrayList because it's easier; convert to array later

		String nextElem = "";

		int curly = 0;	 // curly braces {}
		int square = 0;  // square brackets []
		int parenth = 0; // parentheses ()

		int len = input.length();

		for (int i = 0 ; i < len ; i++) { // go through each character individually

			boolean addChar = true;

			char ch = input.charAt(i);

			if (ch == '{')
				curly++;
			else if (ch == '}')
				curly--;
			else if (ch == '[')
				square++;
			else if (ch == ']')
				square--;
			else if (ch == '(')
				parenth++;
			else if (ch == ')')
				parenth--;
			else if (i == len - 1) {
				nextElem += input.charAt(i);
				outputAL.add("" + nextElem);
			}
			else if (curly == 0 && square == 0 && parenth == 0 && ch == ',') {
				addChar = false;
			}


			if (addChar)
				nextElem += input.charAt(i);
			else {
				outputAL.add("" + nextElem);
				nextElem = "";
			}
		}

		int size = outputAL.size();
		String[] output = new String[size];
		for (int i = 0 ; i < size ; i++) {
			output[i] = outputAL.get(i);
		}

		return output;
	}


	public void save() {
	}

	public void view(GuildMessageReceivedEvent event) {
		inv.view(event,0);
	}

	public void incrementGrains(int i) {

	}


}
