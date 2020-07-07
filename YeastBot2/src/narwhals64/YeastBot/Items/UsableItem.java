package narwhals64.YeastBot.Items;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public abstract class UsableItem extends Item {

	public UsableItem(int id, String name, String desc, String lore) {
		super(id, name, desc, lore);
	}

	public void use(GuildMessageReceivedEvent event) {
		
	}
	
	public void view(GuildMessageReceivedEvent event, int level) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (args.length - 1 == level) { // if the level "matches" the args length (no extra parameters were entered), run the following code
			display(event,level);
		}
		else if (args.length - 1 > level) {  // one extra parameter = specifies item on the page
			if (args[level + 1].equalsIgnoreCase("use") || args[level + 1].equalsIgnoreCase("u")) {
				use(event);
			}
			else {
				super.view(event, level + 1);
			}
		}
	}

	@Override
	public void display(GuildMessageReceivedEvent event, int level) {
		event.getChannel().sendTyping().queue();

		EmbedBuilder embed = new EmbedBuilder();

		Color color = new Color(0x049FFF);
		embed.setColor(color);

		embed.setTitle(getTitle() + " Information");
		embed.setDescription("Extra Parameters:\ns - Sell this item.\nt - Trash this item.\nm - Mail this item.\nu - Use this item.");

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
