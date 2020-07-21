package narwhals64.YeastBot.Commands;

import narwhals64.YeastBot.Games.MiniGames.Pondscum;
import narwhals64.YeastBot.GameInstance;
import narwhals64.YeastBot.YeastBot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class MiniGame extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String args[] = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(YeastBot.getPrefix(event) + "minigame") || args[0].equalsIgnoreCase(YeastBot.prefix + "mg")) {

            int arguments = args.length;

            if (arguments == 1) { // "mg" --> list available commands
                String output = "**Available Commands**\n";
                output += "list = List all open games\n";
                output += "join = Join a game (requires additional parameters)\n";
                output += "create = Create a game (requires additional parameters)\n";
                output += "view = View a particular game's current state (requires additional parameters)\n";
                output += "scope = Change your basic text scope to a particular game (requires additional parameters)\n" +
                        "*Changing your basic text to be in a game's scope means your normal messages will be taken as commands within the game.  *,mg s 0* will disable this.*\n";
                output += "edit = Change the settings of the game";
                output += "leave = Leave the game";
                event.getChannel().sendMessage(output).queue();
            } // "mg" --> List available commands.

            else if (arguments > 1) {

                String param1 = args[1];

                if (param1.equalsIgnoreCase("list") || param1.equalsIgnoreCase("l")) {
                    String output = "**Available Games:**";
                    for (GameInstance gi : YeastBot.minigameInstances) {
                        if (gi.isOpen()) {
                            output += "\n" + gi.getGameIndex() + ": " + gi.getName();
                        }
                    }
                    event.getChannel().sendMessage(output).queue();
                } // "l" --> List running games.

                else if (param1.equalsIgnoreCase("join") || param1.equalsIgnoreCase("j")) {

                    try {
                        int gameToJoin = Integer.parseInt(args[2]) - 1;

                        YeastBot.minigameInstances.get(gameToJoin).addPlayer(event);

                    }
                    catch (Exception e) {
                        event.getChannel().sendMessage("Minigame not found.").queue(); //TODO real error message
                    }
                } // "j" --> Join a game.

                else if (param1.equalsIgnoreCase("create") || param1.equals("c")) {

                    if (arguments > 2) {

                        String param3 = args[2];

                        if (param3.equalsIgnoreCase("pondscum") || param3.equalsIgnoreCase("ps")) {
                            YeastBot.minigameInstances.add(new Pondscum(event));
                            event.getChannel().sendMessage("A new Pondscum game has successfully been created!").queue();
                        }
                    }

                } // "c" --> Create a game.

                else if (param1.equalsIgnoreCase("view") || param1.equalsIgnoreCase("v")) {
                    try {
                        int gameToJoin = Integer.parseInt(args[2]) - 1;

                        event.getChannel().sendMessage(YeastBot.minigameInstances.get(gameToJoin).displayCurrentState()).queue();
                    }
                    catch (Exception e) {

                    }
                } // "v" --> View more information about a particular game.

                else if (param1.equalsIgnoreCase("scope") || param1.equalsIgnoreCase("s")) {

                    try {
                        String param2 = args[2];

                        int scope = Integer.parseInt(param2) - 1;

                        if (scope == -1) { // if 0 is entered
                            YeastBot.gameScopes.put(event.getAuthor().getId(),scope);
                        }
                        else if (!YeastBot.minigameInstances.get(scope).isOpen()) { // if the game is closed
                            event.getChannel().sendMessage("That game is closed.  There's no need to set your scope to it!").queue();
                        }
                        else {
                            YeastBot.gameScopes.put(event.getAuthor().getId(),scope);
                            event.getChannel().sendMessage("Your scope has been set!").queue();
                        }

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        event.getChannel().sendMessage("Sorry, but there was some sort of error in that scope command.").queue();
                    }

                } // "s" --> Change your scope to a particular game.

            }
        }
    }
}
