package narwhals64.YeastBot.Items;

import narwhals64.YeastBot.YeastBot;
import narwhals64.YeastBot.YeastBotProfile;
import narwhals64.YeastBot.Items.Containers.Inventory;
import narwhals64.YeastBot.Items.Containers.TagBag;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public abstract class Item {
	public static Item fetchItem(int id) {
		switch (id) {
		case 0:
			return new Inventory();
		case 1:
			return new TagBag();
		case 100:
			return new Tag();
		case 110:
			return new ThreeLetterTagGacha();
		case 999:
			return new MasonJarOfYeastBotsShum();
		default:
			return new TestItem();
		}
	}
	public static Item fetchItem(String data) {
		int len = data.length();

		String stringId = "";
		int idEnd = 0; // character index at which the id ends
		
		for (int i = 0 ; i < len ; i++) {
			char ch = data.charAt(i);
			if (ch >= 48 && ch <= 57)
				stringId += ch;
			else {
				idEnd = i;
				i = len; // end for loop
			}
		}
		Item output = fetchItem(Integer.parseInt(stringId));
		
		String varData = "";
		int varEnd = idEnd; // by default, it ends on the same character
		int curly = 0; // keep track of curly braces
		for (int i = idEnd ; i < len ; i++) {
			char ch = data.charAt(i);
			if (ch == '{')
				curly++;
			else if (ch == '}')
				curly--;
			
			if (curly == 0) {
				varEnd = i + 1;
				i = len;
			}
			else
				varData += ch; // if we should not end, add the character
		}
		if (!varData.equals("")) {
			output.setVariableData(varData.substring(1)); // the first character, if this isn't empty, will be '{', so we can cut it off
		}
		
		String conData = "";
		int square = 0;
		for (int i = varEnd ; i < len ; i++) {
			char ch = data.charAt(i);
			if (ch == '[')
				square++;
			else if (ch == ']')
				square--;
			
			if (square == 0) {
				i = len;
			}
			else conData += ch;
		}
		if (!conData.equals("")) {
			output.setContainmentData(conData.substring(1)); // the first character, if this isn't empty, will be '[', so we can cut it off
		}
		return output;
	}
	
	YeastBotProfile owner;
	
	private int id;
	private String name;
	private String desc;
	private String lore;
	private String title; // for ,inv
	private int sellPrice;
	
	private int amt;
	private boolean unique;	// if true, this item does not stack. (amt always stays as 1)

	private boolean tradable; // if true, this item cannot be traded between players
	
	
	public Item(int id, String name, String desc, String lore) {
		owner = null;
		
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.lore = lore;
		sellPrice = 0;

		title = "Item";
		amt = 1;
		unique = false;

		tradable = true;
		
	}

	/**
	 * Get the Save Data of the item.
	 * This is a combination of the Item's ID plus the {Variable Data} plus the [Containment Data]
	 * Ex. 12{3}[3{1}:4{3}]
	 * The above example is an item with an ID of 12 with variable data of 3.
	 * It contains an item of ID 3 with variable data 1 and an item of ID 4 with variable data 3.
	 * @return String of Save Data
	 */
	public String getSaveData() {
		String output = "" + id;
		String var = getVariableData();
		if (!var.equals(""))
			output += "{" + var + "}";
		String con = getContainmentData();
		if (!con.equals(""))
			output += "[" + con + "]";
		return output;
	}
	
	public String getVariableData() {
		return "" + amt;
	}
	public String getContainmentData() {
		return "";
	}

	/**
	 * Loads the item based on given variable data
	 * @param data
	 */
	public void setVariableData(String data) {
		String[] sepData = YeastBotProfile.separateWithParams(data);
		setAmt(Integer.parseInt(sepData[0]));
	}
	/**
	 * Loads the item based on given containment data
	 * @param data
	 */
	public void setContainmentData(String data) {
		
	}
	
	public Item setOwner(String id) {
		owner = YeastBot.getProfile(id);
		return this;
	}
	public Item setOwner(YeastBotProfile prof) {
		owner = prof;
		return this;
	}
	public YeastBotProfile getOwner() {
		return owner;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String newName) {
		name = newName;
	}

	public String getDesc() {
		return desc;
	}
	
	public String getLore() {
		return lore;
	}

	public int getSellPrice() {
		return sellPrice;
	}
	public void setSellPrice(int n) {
		sellPrice = n;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	public String getTitle() {
		return title;
	}
	
	public int getAmt() {
		return amt;
	}
	public void setAmt(int n) {
		amt = n;
	}
	public void incrementAmt(int n) {
		amt += n;
	}
	
	public void setUnique() {
		unique = true;
	}
	public boolean getUnique() {
		return unique;
	}

	public void setTradability(boolean tradability) {
		tradable = tradability;
	}


	public void clean() {
		// non-directory items cannot be cleaned, so just sit tight.
	}

	
	/**
	 * Views information about the given item.
	 * For items with extra parameters that allow closer inspection, the int level is used.
	 * @param event The event that 
	 * @param level The index of the parameter we are currently reading.
	 */
	public void view(GuildMessageReceivedEvent event, int level) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (args.length - 1 == level) {
			display(event, level);
		} // No extra parameters.
		else if (args.length - 1 > level) {
			String nextParam = args[level + 1];
			if (nextParam.equalsIgnoreCase("trash") || nextParam.equalsIgnoreCase("t")) {
				trash(event,level + 1);
			}
			else if (nextParam.equalsIgnoreCase("sell") || nextParam.equalsIgnoreCase("s")) {
				sell(event,level + 1);
			}
		} // AT LEAST ONE extra parameter
	}
	public void display(GuildMessageReceivedEvent event, int level) {
		event.getChannel().sendTyping().queue();

		EmbedBuilder embed = new EmbedBuilder();

		Color color = new Color(0x42FFF); // Normal items are this shade of blue.
		embed.setColor(color);

		embed.setTitle(getTitle() + " Information");
		embed.setDescription("Extra Parameters:\ns - Sell the item.\nt - Trash the item.\nm - Mail the item.");

		embed.addField(getName(),getDesc(),false);

		if (!getLore().equals("")) {
			embed.addField("",getLore(),false);
		} // Add Lore if available.

		embed.addField("Amount Owned: " + getAmt(),"",false);
		embed.addField("Item ID: " + getId(),"",false);

		embed.setFooter("YeastBot",event.getGuild().getMemberById(getOwner().getId()).getUser().getAvatarUrl());

		event.getChannel().sendMessage(embed.build()).queue();

		embed.clear();
	}

	/**
	 * Trash an item, decreasing its amount by 1 or a given amount.
	 * @param event
	 * @param level
	 */
	public void trash(GuildMessageReceivedEvent event, int level) {
		if (tradable && event.getAuthor().getId().equals(getOwner().getId())) {
			String[] args = event.getMessage().getContentRaw().split("\\s+");
			if (args.length - 1 == level) {
				incrementAmt(-1);
			} // no extra parameters = trash 1
			else if (args.length - 1 > level) {
				String nextParam = args[level + 1];
				try {
					int n = Integer.parseInt(nextParam);
					if (getAmt() >= n && n >= 0) {
						incrementAmt(-1 * n);
					} else {
						// invalid number error TODO
					}
				} catch (Exception e) {
					// invalid number error TODO
				}
			} // if another parameter is added, probably a number, trash the item that many times
		}
		else {
			// not tradable TODO
		}
	}
	public void sell(GuildMessageReceivedEvent event, int level) {
		if (tradable && event.getAuthor().getId().equals(getOwner().getId())) {

		}
		else {
			// not tradable error TODO
		}
	}
}
