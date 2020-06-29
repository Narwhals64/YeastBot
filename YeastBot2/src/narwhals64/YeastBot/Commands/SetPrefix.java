package narwhals64.YeastBot.Commands;

import narwhals64.YeastBot.Commands.Resources.ErrorMessage;
import narwhals64.YeastBot.YeastBot;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.List;

public class SetPrefix extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String args[] = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(YeastBot.getPrefix(event) + "setprefix") || args[0].equalsIgnoreCase(YeastBot.getPrefix(event) + "sp")) {


            try {
                YeastBot.getGuild(event).setPrefix(args[1]);
                event.getChannel().sendMessage("Your prefix has been set to " + args[1]).queue();
            } catch (Exception e) {

            }


        }
    }
}