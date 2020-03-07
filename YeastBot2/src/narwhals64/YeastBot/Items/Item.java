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
		int idEnd = 0;
		
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
		int varEnd = idEnd;
		int curly = 0; // keep track of curly braces
		for (int i = idEnd ; i < len ; i++) {
			char ch = data.charAt(i);
			if (ch == '{')
				curly++;
			else if (ch == '}')
				curly--;
			
			if (curly == 0) {
				varEnd = i;
				i = len;
			}
			else
				varData += ch; // if we should not end, add the character
		}
		if (!varData.equals(""))
			output.setVariableData(varData.substring(1)); // the first character, if this isn't empty, will be '{', so we can cut it off
		
		String conData = "";
		int conEnd = varEnd;
		int square = 0;
		for (int i = varEnd ; i < len ; i++) {
			char ch = data.charAt(i);
			if (ch == '[')
				square++;
			else if (ch == ']')
				square--;
			
			if (square == 0) {
				conEnd = i;
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
	private boolean unique;	
	
	
	public Item(int id, String name, String desc, String lore) {
		owner = null;
		
		this.id = id;
		this.name = name;
		this.desc = desc;
		this.lore = lore;
		title = "Item";
		amt = 1;
		unique = false;
		
	}
	
	public String getSaveData() {
		String output = "" + id;
		String var = getVariableData();
		if (!var.equals(""))
			output += "{" + var + "}";
		String con = getContainmentData();
		if (!con.contentEquals(""))
			output += "[" + con + "]";
		return output;
	}
	
	public String getVariableData() {
		return "";
	}
	public String getContainmentData() {
		return "";
	}
	
	public void setVariableData(String data) {
		
	}
	public void setContainmentData(String data) {
		
	}
	
	public void setOwner(String id) {
		owner = YeastBot.getProfile(id);
	}
	public void setOwner(YeastBotProfile prof) {
		owner = prof;
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
	public void incrementAmt(int n) {
		amt += n;
	}
	
	public void setUnique() {
		unique = true;
	}
	public boolean getUnique() {
		return unique;
	}
	
	
	/**
	 * Views information about the given item.
	 * For items with extra parameters that allow closer inspection, the int level is used.
	 * @param event The event that 
	 * @param level The current "level" of item (in case of extra parameters).  ",view 3" would view the item with index 3 and be at level 0.
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
		
		embed.setFooter("YeastBot",event.getAuthor().getAvatarUrl());
		
		event.getChannel().sendMessage(embed.build()).queue();
		
		embed.clear();
	}
}
