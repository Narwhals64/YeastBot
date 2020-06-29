package narwhals64.YeastBot.Items;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class UsableItem extends Item {

	public UsableItem(int id, String name, String desc, String lore) {
		super(id, name, desc, lore);
	}

	public void use(GuildMessageReceivedEvent event) {
		
	}
	
	public void view(GuildMessageReceivedEvent event, int level) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (args.length - 1 == level) { // if the level "matches" the args length (no extra parameters were entered), run the following code
			event.getChannel().sendTyping().queue();

			EmbedBuilder embed = new EmbedBuilder();

			embed.setColor(0x42FFF);

			embed.setTitle(getTitle() + " Information");
			embed.setDescription("Extra Parameters:\nuse - Use this item.");

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
		
		else if (args.length - 1 > level) {  // one extra parameter = specifies item on the page
			
			if (args[level + 1].equalsIgnoreCase("use"))
				use(event);
		}
	}
}
