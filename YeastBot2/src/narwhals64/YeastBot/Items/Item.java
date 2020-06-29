package narwhals64.YeastBot.Items;

import narwhals64.YeastBot.YeastBot;
import narwhals64.YeastBot.YeastBotProfile;
import narwhals64.YeastBot.Items.Containers.Inventory;
import narwhals64.YeastBot.Items.Containers.TagBag;
import narwhals64.YeastBot.Items.Containers.TemplateHolder;
import narwhals64.YeastBot.Items.Weapons.WoodenTrainingSword;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class Item {
	public static Item fetchItem(int id) {
		switch (id) {
		case 0:
			return new Inventory();
		case 1:
			return new TemplateHolder();
		case 2:
			return new TagBag();
		case 11:
			return new Tag();
		case 12:
			return new ThreeLetterTagGacha();
		case 999:
			return new MasonJarOfYeastBotsShum();
		case 1000:
			return new WoodenTrainingSword();
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
	
	private int amt;
	private boolean unique;	// if true, this item does not stack. (amt always stays as 1)

	private boolean tradable; // if true, this item cannot be traded between players
	
	
	public Item(int id, String name, String desc, String lore) {
		owner = null;
		
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.lore = lore;
		title = "Item";
		amt = 1;
		unique = false;

		tradable = true;
		
	}
	
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
	
	public void setVariableData(String data) {
		String[] sepData = YeastBotProfile.separateWithParams(data);
		setAmt(Integer.parseInt(sepData[0]));
	}
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
		event.getChannel().sendTyping().queue();
		
		EmbedBuilder embed = new EmbedBuilder();
		
		embed.setColor(0x42FFF);
		
		embed.setTitle(title + " Information");
		embed.setDescription("This item has no extra parameters.");
			
		embed.addField(getName(),getDesc(),false);
		if (!getLore().equals("")) {
			embed.addField("",getLore(),false);
		}
		embed.addField("Amount Owned: " + getAmt(),"",false);
		embed.addField("Item ID: " + getId(),"",false);
		
		embed.setFooter("YeastBot",event.getGuild().getMemberById(getOwner().getId()).getUser().getAvatarUrl());
		
		event.getChannel().sendMessage(embed.build()).queue();
		
		embed.clear();
	}
}
