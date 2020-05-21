package narwhals64.YeastBot.Commands;

import narwhals64.YeastBot.CardGames.GamePlayer;
import narwhals64.YeastBot.GameInstance;
import narwhals64.YeastBot.YeastBot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Game extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String args[] = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(YeastBot.prefix + "game") || args[0].equalsIgnoreCase(YeastBot.prefix + "g")) {

            int arguments = args.length;

            if (arguments == 1) { // "jg" --> list available games

            }

            else if (arguments == 2) {

                if (args[1].equalsIgnoreCase("list")) {
                    String output = "**Available Games:**";
                    int index = 0;
                    for (GameInstance gi : YeastBot.gameInstances) {
                        index++;
                        output += "\n(" + index + ")  " + gi.toString();
                    }
                    event.getChannel().sendMessage(output).queue();
                }

                else if (args[1].equalsIgnoreCase("join")) {

                    try {
                        int gameToJoin = Integer.parseInt(args[2]);

                        YeastBot.gameInstances.get(gameToJoin).addPlayer(event.getAuthor());

                    }
                    catch (Exception e) {
                        event.getChannel().sendMessage("Game not found.").queue(); //TODO real error message
                    }
                }



            }



        }
    }
}
