package narwhals64.YeastBot.Commands;

import narwhals64.YeastBot.YeastBot;
import narwhals64.YeastBot.YeastBotProfile;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Inv extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String args[] = event.getMessage().getContentRaw().split("\\s+");
		
		if (args[0].equalsIgnoreCase(YeastBot.prefix + "inv")) {
			YeastBotProfile prof = YeastBot.getProfile(event.getAuthor().getId());
			
			prof.view(event);
		}
	}
}
