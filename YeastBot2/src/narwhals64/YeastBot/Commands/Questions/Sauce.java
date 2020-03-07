package narwhals64.YeastBot.Commands.Questions;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Sauce extends ListenerAdapter {
	public Sauce(GuildMessageReceivedEvent event) {
		int rand = (int)(Math.random() * 298000);
		
		event.getChannel().sendMessage("https://nhentai.net/g/" + rand + "/").queue();
		
		/*
		EmbedBuilder embed = new EmbedBuilder();
		embed.setColor(0xE52853);
		
		embed.setTitle("Sauce","https://nhentai.net/g/" + rand + "/");
		embed.setDescription("Did someone say sauce?");
		embed.setFooter("YeastBot",event.getAuthor().getAvatarUrl());
		
		event.getChannel().sendMessage(embed.build()).queue();
		*/
	}
}
