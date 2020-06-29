package narwhals64.YeastBot.Commands;

import narwhals64.YeastBot.YeastBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;

public class Fish extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String args[] = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(YeastBot.getPrefix(event) + "fish")) {

            EmbedBuilder fish = new EmbedBuilder();


            fish.setTitle("FISH!");
            fish.setDescription(":fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish:\n:fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish::fish:");
            fish.setColor(new Color(0, 255, 0));

            int amt = 5;

            if (args.length >= 2) {
                try {
                    amt = Integer.parseInt(args[1]);
                    event.getChannel().sendMessage("Alright, I'll tell you to fish " + amt + " times!").queue();
                }
                catch (Exception e) {
                    event.getChannel().sendMessage("I didn't receive an integer so I'll tell you to fish 5 times!").queue();
                }
            }

            for (int i = 0; i < amt - 1; i++) {
                event.getChannel().sendMessage(fish.build()).queue();

                try {
                    Thread.sleep(3500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            fish.setColor(new Color(255,0,0));
            event.getChannel().sendMessage(fish.build()).queue();

            event.getChannel().sendMessage("Tell me again if you want me to be your fishing coach!").queue();

        }
    }
}
