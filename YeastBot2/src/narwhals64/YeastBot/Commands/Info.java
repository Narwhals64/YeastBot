package narwhals64.YeastBot.Commands;

import narwhals64.YeastBot.YeastBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Info extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String args[] = event.getMessage().getContentRaw().split("\\s+");
		
		if (args[0].equalsIgnoreCase(YeastBot.prefix + "info")) {
			event.getChannel().sendTyping().queue();
			
			EmbedBuilder embed = new EmbedBuilder();
			embed.setColor(0x5c3092);
			
			embed.setTitle("YeastBot Information");
			embed.setDescription("YeastBot is the single greatest Discord bot to ever grace the Earth.");
			
			embed.addField("Creator:","Narwhals64",false);
			
			embed.setFooter("YeastBot",event.getAuthor().getAvatarUrl());
			
			event.getChannel().sendMessage(embed.build()).queue();
			
			embed.clear();
		}
	}
}
