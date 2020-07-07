package narwhals64.YeastBot.Items.Containers;

import narwhals64.YeastBot.Commands.Resources.ErrorMessage;
import narwhals64.YeastBot.YeastBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class Inventory extends ItemDirectory {

	public Inventory() {
		super(0, "Inventory", "The entire player Inventory.", "");
		setTitle("Inventory");
		setUnique();
		setTradability(false);
	}

	public void view(GuildMessageReceivedEvent event, int level) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (args.length - 1 == level) {
			super.view(event,level);
		}

		else if (args[1].startsWith("<@!") && args[1].endsWith(">")) {
			//YeastBot.getProfile(args[1].substring(3,20)).view(event,level+1);
			event.getChannel().sendMessage("Viewing other people's inventories is not yet implemented.").queue();
		}
		else {
			super.view(event,level);
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

		embed.addField("Currencies:","**Crumbs**: " + getOwner().getCrumbs() + "\n**Loaves**: " + getOwner().getLoaves(),false);

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

	public String getSaveData() {
		return getContainmentData();
	}
}
