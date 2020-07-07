package narwhals64.YeastBot.Commands;

import java.util.List;

import narwhals64.YeastBot.YeastBot;
import narwhals64.YeastBot.Commands.Resources.ErrorMessage;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BasicText extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {


        String message = event.getMessage().getContentRaw();

        if (!message.startsWith(",") && !event.getAuthor().isBot()) {
            try {
                int scope = YeastBot.gameScopes.get(event.getAuthor().getId());

                if (scope != -1)
                    YeastBot.minigameInstances.get(scope).enterCommand(event);


            } catch (Exception e) {

            }


        }

    }
}
