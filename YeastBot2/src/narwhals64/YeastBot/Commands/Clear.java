package narwhals64.YeastBot.Commands;

import java.util.List;

import narwhals64.YeastBot.YeastBot;
import narwhals64.YeastBot.Commands.Resources.ErrorMessage;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Clear extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String args[] = event.getMessage().getContentRaw().split("\\s+");
		
		if (args[0].equalsIgnoreCase(YeastBot.getPrefix(event) + "clear")) {
			if (args.length < 2) { // Error message.
				new ErrorMessage(event, ",clear","Clear","Incorrect usage of command.","An invalid amount of arguments was detected.  Make sure the command is written correctly with the correct amount of parameters.");
			}
			else if (!event.getGuild().getMemberById(event.getAuthor().getId()).hasPermission(Permission.ADMINISTRATOR)) {
				new ErrorMessage(event, ",clear","Clear","Invalid user.","Only users with Administrative powers are able to use this command.");
			}
			else if (event.getAuthor().isBot()) {
				
			}
			else {
				try {
					List<Message> messages = event.getChannel().getHistory().retrievePast(Integer.parseInt(args[1])).complete();
					event.getChannel().deleteMessages(messages).queue();
				} catch (IllegalArgumentException e) {
					if (e.toString().startsWith("java.lang.IllegalArgumentException: Message retrieval limit")) { // Too many messages.
						new ErrorMessage(event, ",clear","Clear","Too many messages.  Discord API caught an exception.","Discord only allows bots to clear 100 messages at one time.  Do not command YeastBot to clear any more than 100 or less than 1.");
					}
					else if (e.toString().startsWith("java.lang.IllegalArgumentException: Message Id")) { // Messages are too old.
						new ErrorMessage(event, ",clear","Clear","Messages are too old.  Discord API caught an exception.","Discord only allows bots to clear messages that are not over two weeks old.  Do not command YeastBot to clear messages that are older than two weeks.  Note that this error may appear if the command \"clear 1\" was typed.");
					}
					else if (e.toString().startsWith("java.lang.NumberFormatException: For input string:")){ 
						new ErrorMessage(event, ",clear","Clear","Invalid input.  Java caught an exception.","Java was not able to parse the input as an integer.  Negative numbers or strings containing non-numeric characters will not work.");
					}
					else {
						new ErrorMessage(event, ",clear","Clear","Unknown error.","YeastBot was not able to find out what the error was here.  Contact Narwhals64 and tell him his code is broken.");
					}
				}
			}
		}
	}
}
