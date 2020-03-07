package narwhals64.YeastBot.Items;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class UsableItem extends Item {

	public UsableItem(int id, String name, String desc, String lore) {
		super(id, name, desc, lore);
	}

	public void use(GuildMessageReceivedEvent event) {
		
	}
	
	public void view(GuildMessageReceivedEvent event, int level) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (args.length == level + 1) { // if the level "matches" the args length (no extra parameters were entered), run the following code
			super.view(event, level);
		}
		
		else if (args.length == level + 2) {  // one extra parameter = specifies item on the page
			
			if (args[level + 1].equalsIgnoreCase("use"))
				use(event);
		}
	}
}
