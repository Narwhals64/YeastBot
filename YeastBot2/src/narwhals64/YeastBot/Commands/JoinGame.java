package narwhals64.YeastBot.Commands;

import narwhals64.YeastBot.CardGames.PileTypes.Deck;
import narwhals64.YeastBot.CardGames.Pondscum;
import narwhals64.YeastBot.GameInstance;
import narwhals64.YeastBot.YeastBot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class JoinGame extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String args[] = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(YeastBot.prefix + "joingame") || args[0].equalsIgnoreCase(YeastBot.prefix + "jg")) {

            int arguments = args.length;

            if (arguments == 1) { // "jg" --> list available games
                String output = "**Available Games:**";
                int index = 1;
                for (GameInstance gi : YeastBot.gameInstances) {
                    index++;
                    output += "\n(" + index + ")  " + gi.toString();
                }
                event.getChannel().sendMessage(output).queue();
            }

            else if (arguments == 2) {
                int game = Integer.parseInt(args[1]);

                if (game > YeastBot.gameInstances.size() || game < 0)
                    event.getChannel().sendMessage("Please enter a valid number.").queue();
                else {
                    YeastBot.gameInstances.get(game - 1).addPlayer(event.getAuthor());
                    event.getChannel().sendMessage("You have been added to " + YeastBot.gameInstances.get(game - 1)).queue();
                }
            }



        }
    }
}
