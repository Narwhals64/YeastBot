package narwhals64.YeastBot.Commands;

import narwhals64.YeastBot.CardGames.GamePlayer;
import narwhals64.YeastBot.CardGames.GameTypes.Pondscum;
import narwhals64.YeastBot.GameInstance;
import narwhals64.YeastBot.YeastBot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Game extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String args[] = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(YeastBot.prefix + "game") || args[0].equalsIgnoreCase(YeastBot.prefix + "g")) {

            int arguments = args.length;

            if (arguments == 1) { // "g" --> list available commands
                String output = "**Available Commands**\n";
                output += "list = List all open games\n";
                output += "join = Join a game (requires additional parameters)\n";
                output += "create = Create a game (requires additional parameters)\n";
                output += "view = View a particular game's current state (requires additional parameters)\n";
                event.getChannel().sendMessage(output).queue();
                output += "scope = Change your basic text scope to a particular game (requires additional parameters)\n" +
                        "*Changing your basic text to be in a game's scope means your normal messages will be taken as commands within the game.  *,g s 0* will disable this.*";
                event.getChannel().sendMessage(output).queue();
            }

            else if (arguments > 1) {

                String param2 = args[1];

                if (param2.equalsIgnoreCase("list") || param2.equalsIgnoreCase("l")) {
                    String output = "**Available Games:**";
                    int index = 0;
                    for (GameInstance gi : YeastBot.gameInstances) {
                        if (gi.isOpen()) {
                            output += "\n" + gi.getGameIndex() + ": " + gi.getName();
                        }
                    }
                    event.getChannel().sendMessage(output).queue();
                }

                else if (param2.equalsIgnoreCase("join") || param2.equalsIgnoreCase("j")) {

                    try {
                        int gameToJoin = Integer.parseInt(args[2]) - 1;

                        YeastBot.gameInstances.get(gameToJoin).addPlayer(event.getAuthor());

                    }
                    catch (Exception e) {
                        event.getChannel().sendMessage("Game not found.").queue(); //TODO real error message
                    }
                }

                else if (param2.equalsIgnoreCase("create") || param2.equals("c")) {

                    if (arguments > 2) {

                        String param3 = args[2];

                        if (param3.equalsIgnoreCase("pondscum") || param3.equalsIgnoreCase("ps")) {
                            YeastBot.gameInstances.add(new Pondscum(event, event.getAuthor()));
                        }
                    }

                }

                else if (param2.equalsIgnoreCase("view") || param2.equalsIgnoreCase("v")) {
                    try {
                        int gameToJoin = Integer.parseInt(args[2]) - 1;

                        event.getChannel().sendMessage(YeastBot.gameInstances.get(gameToJoin).displayCurrentState()).queue();
                    }
                    catch (Exception e) {

                    }
                }

                else if (param2.equalsIgnoreCase("scope") || param2.equalsIgnoreCase("s")) {



                }

            }

        }
    }
}
