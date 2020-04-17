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
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class YeastBotProfile {
	private String id;
	
	private int grains;
	private int loaves;
	
	private int wins;
	private int losses;
	private int draws;
	
	private Inventory inv;
		
	
	public YeastBotProfile(String id) {
		this.id = id;
	}
	
	@SuppressWarnings("unchecked")
	public void load() {
		try {
			inv = new Inventory();
			inv.setOwner(id);
			
			BufferedReader br = new BufferedReader(new FileReader(new File(YeastBot.profilesPath + id + ".txt")));
			
			// Line 1 - currency
			String line = br.readLine();
			
			if (line == null || line.equals("")) {
				grains = 1000;
				loaves = 0;
			}
			else {
				String[] elems = line.split(":"); // divide the line by the necessary dividers.  The dividers are : then ; then . then , but not all of them are always used
				
				if (elems.length >= 1 && elems[0] != null)
					grains = Integer.parseInt(elems[0]);
				if (elems.length >= 2 && elems[1] != null)
					loaves = Integer.parseInt(elems[1]);
			}
			
			// Line 2 - inventory
			line = br.readLine();
			
			inv = new Inventory();
			inv.setOwner(this);
			
			if (line == null || line.equals("")) {
				inv.addItem(Item.fetchItem(1));
				inv.addItem(Item.fetchItem(2));
				inv.addItem(Item.fetchItem(1000));
			}
			else {
				inv.setContainmentData(line);
			}
			
			// Line 3 - profile statistics
			line = br.readLine();
			System.out.println(line);
			
			
			
			if (line == null || line.equals("")) {
				wins = 0;
				losses = 0;
				draws = 0;
			}
			else {
				String[] elems = line.split(":");
				
				if (elems.length >= 1 && elems[0] != null)
					wins = Integer.parseInt(elems[0]);
				if (elems.length >= 2 && elems[1] != null)
					losses = Integer.parseInt(elems[1]);
				if (elems.length >= 3 && elems[2] != null)
					draws = Integer.parseInt(elems[2]);
			}
			
			
			
			
			br.close();
			
		}
		catch (Exception e) {
			System.out.println("A profile could not be loaded for some reason.");
		}
	}
	
	public void save() {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(YeastBot.profilesPath + id + ".txt"));
			// Line 1 - currency
			bw.write("" + grains + ":" + loaves);
			bw.newLine();
			// Line 2 - inventory			
			bw.write(inv.getContainmentData());
			bw.newLine();
			// Line 3 - profile statistics
			bw.write("" + wins + ":" + losses + ":" + draws);
			
			
			bw.close();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("A file could not be saved for some reason.");
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
	
	public String getId() {
		return id;
	}
	
	public void incrementGrains(int n) {
		grains += n;
		save();
	}
	

	
	@SuppressWarnings("unchecked")
	public void addItem(Item newItem) {
		inv.addItem(newItem);
	}
	@SuppressWarnings("unchecked")
	public void addItem(int itemId) {
		inv.addItem(Item.fetchItem(itemId));
	}
	public Item findItem(int itemId) {
		return inv.findItem(itemId);
	}
	
	public void view(GuildMessageReceivedEvent event) {
		inv.view(event, 0);
	}
	
	
	
	


}
