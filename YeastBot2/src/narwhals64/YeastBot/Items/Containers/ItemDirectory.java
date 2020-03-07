package narwhals64.YeastBot.Items.Containers;

import java.util.ArrayList;

import narwhals64.YeastBot.YeastBotProfile;
import narwhals64.YeastBot.Commands.Resources.ErrorMessage;
import narwhals64.YeastBot.Items.Item;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class ItemDirectory<T extends Item> extends Item {
	private ArrayList<T> items;
	
	public ItemDirectory(int id, String name, String desc, String lore) {
		super(id,name,desc,lore);
		setTitle("Item Directory");
		setUnique();
		items = new ArrayList<T>();
	}
	
	
	public String getContainmentData() {
		int size = items.size();
		String output = "";
		for (int i = 0 ; i < size ; i++) {
			output += items.get(i).getSaveData();
			if (i != size - 1)
				output += ",";
		}
		return output;
	}
	
	public void addItem(T newItem) {
		items.add(newItem);
		getOwner().save();
	}
	
	public T findItem(int itemId) {
		for (T i : items) 
			if (i.getId() == itemId)
				return i;
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public void setContainmentData(String data) {
		String[] elems = YeastBotProfile.separateWithParams(data);
		System.out.println("data: " + data);
		System.out.println("len: " + elems.length);
		for (String str : elems) {
			System.out.println("adding " + str);
			items.add((T) Item.fetchItem(str));
		}
	}
	
	
	
	@Override
	public void view(GuildMessageReceivedEvent event, int level) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		level++; // the level is essentially the amount of parameters we've read so far.  For each iteration, we will always look at the next parameter inherently.
		
		if (args.length == level) { // if the level "matches" the args length (no extra parameters were entered), run the following code
			EmbedBuilder embed = new EmbedBuilder();
			
			embed.setColor(0xC70000);
			
			embed.setTitle(getTitle() + " Information");
			embed.setDescription("Extra Parameters:\nn - View Item on page.\npage n - View page.");
			
			embed.addField(getName(),getDesc(),false);

			embed.addField("Items in " + getTitle() + ":",invToString(1),false);
			
			embed.addField("Item ID: " + getId(),"",false);
			
			embed.setFooter("YeastBot",event.getAuthor().getAvatarUrl());
			
			event.getChannel().sendMessage(embed.build()).queue();
		}
		
		else if (args.length == level + 1) {  // one extra parameter = specifies item on the page
			
			int itemIndex = Integer.parseInt(args[level]) - 1;
			
			try {
				items.get(itemIndex).view(event, level);
			}
			catch (Exception e) {
				new ErrorMessage(event,"View Item",",inv","View an item in the user's inventory or an item directory.","Did you enter an invalid number?");
			}
		}
		
		else if (args.length == level + 2) { // two extra parameters
			if (args[level].equalsIgnoreCase("page")) {
				
			}
		}
	}
	
	public String invToString(int page) {
		// if the page is too high or too low then have it loop around.  page 0 = first page
		int len = items.size();
		int itemsPerPage = 15;
		
		
		int totalPages = (len/itemsPerPage + 1);
					// second part of this equation is total number of pages
		page = page%totalPages;
		int itemsPerThisPage = itemsPerPage;
		
		if (page == totalPages - 1) { // if the page is the last page
			itemsPerThisPage = len%itemsPerPage;
		}
		
		String output = "";
		for (int i = 0 ; i < itemsPerThisPage ; i++) { // for 0 to the amount of items per this page
			int index = itemsPerPage * (page) + i; // index is the actual index of the item, not the index of the item on the page.  i is the item's page index.  this uses itemsPerPage because even if this page does not have the same constant amount, the other pages do.
			if (index >= items.size()) { // if the index is greater than or equal to the amount of items per page, end the for loop
				i = itemsPerThisPage; // this ends the for loop
			}
			else {
				output += "" + (itemsPerPage * (page) + i + 1) + ": " + items.get(i).getName(); // add a string in the form n: item x amount
				if (items.get(i).getAmt() > 1)
					output += " x " + items.get(i).getAmt();
				if (i != itemsPerThisPage - 1)
					output += "\n";
			}
		}
		
		return output;
	}
}
