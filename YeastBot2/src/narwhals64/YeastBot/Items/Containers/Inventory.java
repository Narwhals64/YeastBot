package narwhals64.YeastBot.Items.Containers;

import narwhals64.YeastBot.Commands.Resources.ErrorMessage;
import narwhals64.YeastBot.YeastBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Inventory extends ItemDirectory {

	public Inventory() {
		super(0, "Inventory", "The entire player Inventory.", "How the hell are you viewing this as an item?  There must have been a mistake somewhere.");
		setTitle("Inventory");
	}

	public void view(GuildMessageReceivedEvent event, int level) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");

		if (args.length - 1 == level) {
			super.view(event,level);
		}

		else if (args[1].startsWith("<@!") && args[1].endsWith(">")) {
			YeastBot.getProfile(args[1].substring(3,20)).view(event,level+1);
		}
		else {
			super.view(event,level);
		}

	}

	public String getSaveData() {
		return getContainmentData();
	}
}
