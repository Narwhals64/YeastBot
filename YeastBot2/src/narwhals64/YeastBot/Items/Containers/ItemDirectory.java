package narwhals64.YeastBot.Items.Containers;

import java.awt.*;
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

	

	public int getSize() {
		return items.size();
	}

	public Item getItem(int n) {
		return items.get(n);
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


	public Item removeItem(int n) {
		return items.remove(n);
	}

	public String getContainmentData() {
		int size = items.size();
		String output = "";
		for (int i = 0 ; i < size ; i++) {
			output += items.get(i).getSaveData();
			if (i != size - 1)
				 output += ":";
		}
		return output;
	}

	@SuppressWarnings("unchecked")
	public void setContainmentData(String data) {
		String[] elems = YeastBotProfile.separateWithParams(data);
		for (String str : elems) {
			items.add((T) Item.fetchItem(str).setOwner(getOwner()));
		}

	}


	public void clean() {
		int size = getSize();
		for (int i = 0 ; i < size ; i++) {
			if (getItem(i).getAmt() <= 0) {
				removeItem(i);
				i--;
				size--;
			}
			else {
				getItem(i).clean();
			}
		}
	}



	@Override
	public void view(GuildMessageReceivedEvent event, int level) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (args.length - 1 == level) {
			display(event,level);
		}
		else if (args.length - 1 > level) {  // if there are more parameters, move on
			try {
				int itemIndex = Integer.parseInt(args[level + 1]) - 1;

				items.get(itemIndex).view(event, level + 1);
			}
			catch (Exception e) {
				new ErrorMessage(event,"View Item",",inv","View an item in the user's inventory or an item directory.","Did you enter an invalid number?");
			}
		}
	}

	@Override
	public void display(GuildMessageReceivedEvent event, int level) {
		event.getChannel().sendTyping().queue();

		EmbedBuilder embed = new EmbedBuilder();

		Color color = new Color(0xC70000); // All containers are this shade of red.
		embed.setColor(color);

		embed.setTitle(getTitle() + " Information");
		embed.setDescription("Extra Parameters:\n(a number) - View an item at the given index.");

		embed.addField(getName(),getDesc(),false);

		if (!getLore().equals("")) {
			embed.addField("",getLore(),false);
		} // Add Lore if available.

		embed.addField("Items in " + getTitle() + ":",invToString(1),false);
		embed.addField("Item ID: " + getId(),"",false);

		embed.setFooter("YeastBot",event.getGuild().getMemberById(getOwner().getId()).getUser().getAvatarUrl());

		event.getChannel().sendMessage(embed.build()).queue();

		embed.clear();
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
