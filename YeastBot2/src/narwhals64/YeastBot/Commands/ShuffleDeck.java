package narwhals64.YeastBot.Commands;

import narwhals64.YeastBot.CardGames.PileTypes.Deck;
import narwhals64.YeastBot.CardGames.Pondscum;
import narwhals64.YeastBot.YeastBot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ShuffleDeck extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String args[] = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(YeastBot.prefix + "shuffledeck") || args[0].equalsIgnoreCase(YeastBot.prefix + "sd")) {
            Deck d = new Deck();
            event.getChannel().sendMessage(d.toString()).queue();
            d.flipDeck();
            event.getChannel().sendMessage(d.toString()).queue();
            d.shuffle();
            event.getChannel().sendMessage(d.toString()).queue();
            d.flipDeck();
            event.getChannel().sendMessage(d.toString()).queue();

            Pondscum ps = new Pondscum(event);
            YeastBot.gameInstances.add(ps);
            ps.addPlayer(event.getAuthor());
            ps.joinPlayers();
            ps.initializeRound();
        }
    }
}
