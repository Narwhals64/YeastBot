package narwhals64.YeastBot.Commands;

import narwhals64.YeastBot.YeastBot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Say extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String args[] = event.getMessage().getContentRaw().split("\\s+");
		
		if (args[0].equalsIgnoreCase(YeastBot.getPrefix(event) + "say")) {
			String message = event.getMessage().getContentRaw().substring(YeastBot.getPrefix(event).length() + 4);
			
			event.getChannel().deleteMessageById(event.getMessage().getId()).queue();
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage(message).queue();
		}
	}
}
