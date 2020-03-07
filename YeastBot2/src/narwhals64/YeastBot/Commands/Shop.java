package narwhals64.YeastBot.Commands;

import narwhals64.YeastBot.YeastBot;
import narwhals64.YeastBot.YeastBotProfile;
import narwhals64.YeastBot.Commands.Resources.ErrorMessage;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Shop extends ListenerAdapter{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String args[] = event.getMessage().getContentRaw().split("\\s+");
		
		if (args[0].equalsIgnoreCase(YeastBot.prefix + "shop")) {
			YeastBotProfile prof = YeastBot.getProfile(event.getAuthor().getId());
			
			if (args.length == 1) {
				event.getChannel().sendMessage("For testing purposes, the only item you are currently allowed to buy is a **Three-Letter Tag Gacha**.  It costs 5 Grains.  Type \",shop buy 1\" to buy it.").queue();
			}
			else {
				if (args.length >= 2 && args[1].toLowerCase().equals("buy")) {
					if (args.length != 3)
						new ErrorMessage(event, ",shop","View and buy items from the Shop.","It appears not enough paramaters were entered.","If you typed \\\"buy\\\" then make sure you select an item.");
					else {
						System.out.println(Integer.parseInt(args[2]));
						switch (Integer.parseInt(args[2])) {
						case 1:
							prof.incrementGrains(-5);
							prof.addItem(12);
							prof.save();
						}
					}
				}
			}	
		}
	}
}
