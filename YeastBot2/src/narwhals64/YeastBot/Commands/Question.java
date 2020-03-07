package narwhals64.YeastBot.Commands;

import narwhals64.YeastBot.Commands.Questions.Sauce;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Question extends ListenerAdapter {
	public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		int size = args.length;
				
		if (event.getMessage().getMentionedUsers().size() > 0 && event.getMessage().getMentionedUsers().get(0).getId().equals("243061750113632258")) { // confirms YeastBot ping
			if (args[size - 1].endsWith("?")) { // confirms question
				if (event.getMessage().getContentRaw().toLowerCase().contains("sauce")) {
					new Sauce(event);
				}
				else {
					int rand = (int)(Math.random() * 10);
					String response;
					if (rand >= 0 && rand <= 2) {
						response = "Yes.";
					}
					else if (rand == 3) {
						response = "Yes, absolutely!";
					}
					else if (rand == 4) {
						response = "Hmmm... maybe?";
					}
					else if (rand >= 5 && rand <= 7) {
						response = "No.";
					}
					else if (rand == 8) {
						response = "No, absolutely not!";
					}
					else {
						response = "Uhhh... I don't think so.";
					}
					
					event.getChannel().sendMessage(response).queue();
				}
			}
		}
	}
}
