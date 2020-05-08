package narwhals64.YeastBot.Commands.Resources;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ErrorMessage extends ListenerAdapter {
	public ErrorMessage(GuildMessageReceivedEvent event, String command, String function, String description, String solution) {

		EmbedBuilder error = new EmbedBuilder();

		error.setColor(0xDB0000);
		error.setTitle("Error!");
		error.setDescription("YeastBot has encountered an error!  This embed is the error log.  Apologies for the inconvenience (unless it's blatantly your fault).");

		error.addField("Command:",command,false);
		error.addField("Function:",function,false);
		error.addField("Description:",description,false);
		error.addField("Solution:",solution,false);

		error.setFooter("YeastBot",event.getAuthor().getAvatarUrl());

		event.getChannel().sendMessage(error.build()).queue();

		error.clear();
	}
}
