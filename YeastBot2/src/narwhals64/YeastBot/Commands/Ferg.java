package narwhals64.YeastBot.Commands;

import narwhals64.YeastBot.YeastBot;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;



public class Ferg extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String args[] = event.getMessage().getContentRaw().split("\\s+");

        if (args[0].equalsIgnoreCase(YeastBot.prefix + "ferg")) {

            try {
                event.getGuild().getMemberById(args[1]).getUser().openPrivateChannel().queue((channel) -> {
                    channel.sendMessage(ferg1()).queue();
                    channel.sendMessage(ferg2()).queue();
                    channel.sendMessage(ferg3()).queue();
                });
            } catch (Exception e) {
                try {
                    event.getGuild().getMemberById(event.getMessage().getMentionedUsers().get(0).getId()).getUser().openPrivateChannel().queue((channel) -> {
                        channel.sendMessage(ferg1()).queue();
                        channel.sendMessage(ferg2()).queue();
                        channel.sendMessage(ferg3()).queue();
                    });
                } catch (Exception f) {

                }
            }

        }
    }

    public String ferg1() {
        return "Listen up, y'all, 'cause this is it\n" +
                "The beat that I'm bangin' is de-li-cious\n" +
                "Fergalicious definition make them boys go loco\n" +
                "They want my treasure, so they get their pleasures from my photo\n" +
                "You could see me, you can't squeeze me\n" +
                "I ain't easy, I ain't sleazy\n" +
                "I got reasons why I tease 'em\n" +
                "Boys just come and go like seasons\n" +
                "Fergalicious (so delicious)\n" +
                "But I ain't promiscuous\n" +
                "And if you was suspicious\n" +
                "All that shit is fictitious\n" +
                "I blow kisses (mwah)\n" +
                "That puts them boys on rock, rock\n" +
                "And they be lining down the block just to watch what I got (four, tres, two, uno)\n" +
                "So delicious aye aye aye(It's hot, hot)\n" +
                "It's so delicious aye aye aye(I put them boys on rock, rock)\n" +
                "It's so delicious aye aye aye (They wanna taste of what I got)\n" +
                "I'm Fergalicious (t-t-t-t-t-tasty, tasty)\n" +
                "Fergalicious def-, fergalicious def-, fergalicious def\n" +
                "Fergalicious definition make them boys go crazy\n" +
                "They always claim they know me\n" +
                "Comin' to me, callin' me Stacy (Hey, Stacy)\n" +
                "I'm the F to the E, R, G the I the E\n" +
                "And can't no other lady put it down like me\n" +
                "I'm Fergalicious (so delicious)\n" +
                "My body stay vicious\n" +
                "I be up in the gym just working on my fitness\n" +
                "He's my witness (oh, wee)\n" +
                "I put yo' boy on rock, rock\n" +
                "And he be lining down the block just to watch what I got (four, tres, two, uno)\n" +
                "So delicious (It's hot, hot)\n" +
                "So delicious (I put them boys on rock, rock)\n" +
                "So delicious (They wanna taste of what I got)\n" +
                "I'm Fergalicious (hold, hold, hold up, check it out)";
    }
    public String ferg2() {
        return "Baby, baby, baby\n" +
                "If you really want me\n" +
                "Honey, get some patience\n" +
                "Maybe then you'll get a taste\n" +
                "I'll be tasty, tasty\n" +
                "I'll be laced with lacy\n" +
                "It's so tasty, tasty\n" +
                "It'll make you crazy\n" +
                "T to the A to the S T Y, girl, you tasty, T to the A to the S T E Y, girl, you tasty\n" +
                "D to the E to the L I C I O U S; D to the E to the, to the, to the, hit it, Fergie\n" +
                "All the time I turn around brothas gather round\n" +
                "Always looking at me up and down looking at my (uh)\n" +
                "I just wanna say it\n" +
                "Now, I ain't tryin' to round up drama, little mama\n" +
                "I don't wanna take your man\n" +
                "And I know I'm comin' off just a little bit conceited\n" +
                "And I keep on repeating how the boys wanna eat it\n" +
                "But I'm tryin' to tell that I can't be treated like clientele\n" +
                "Cause they say she\n" +
                "Delicious (so delicious)\n" +
                "But I ain't promiscuous\n" +
                "And if you was suspicious\n" +
                "All that shit is fictitious\n" +
                "I blow kisses (mwah)\n" +
                "That puts them boys on rock, rock\n" +
                "And they be lining down the block just to watch what I got (got, got, got)\n" +
                "Four, two, tres, uno.";
    }
    public String ferg3() {
        return "My body stay vicious\n" +
                "I be up in the gym just working on my fitness\n" +
                "He's my witness (oh, wee)\n" +
                "I put yo' boy on rock, rock\n" +
                "And he be lining down the block just to watch what I got (four, tres, two, uno)\n" +
                "It's so delicious (aye, aye, aye, aye)\n" +
                "So delicious (aye, aye, aye, aye)\n" +
                "So delicious (aye, aye, aye, aye)\n" +
                "I'm Fergalicious, t-t-t-t-t-tasty tasty\n" +
                "It's so delicious (aye, aye, aye, aye)\n" +
                "So delicious (aye, aye, aye, aye)\n" +
                "So delicious (aye, aye, aye, aye)\n" +
                "I'm Fergalicious, t-t-t-t-t-tasty tasty\n" +
                "T to the A, to the S T E Y, girl, you tasty\n" +
                "T to the A, to the S T E Y, girl, you tasty\n" +
                "T to the A, to the S T E Y, girl, you tasty\n" +
                "T to the A, to the, to the, to the, to the (four, tres, two, uno)\n" +
                "To the D to the E to the L I C I O U S\n" +
                "To the D to the E to the L I C I O U S\n" +
                "To the D to the E to the L I C I O U S\n" +
                "To the D to the E to the, to the, to the, to the, to the (four, tres, two, uno)\n" +
                "T to the A, to the S T E Y, girl, you tasty\n" +
                "T to the A, to the S T E Y, girl, you tasty\n" +
                "T to the A, to the S T E Y, girl, you tasty\n" +
                "T to the A, to the, to the, to the, to the (four, tres, two, uno)\n" +
                "To the D to the E to the L I C I O U S\n" +
                "To the D to the E to the L I C I O U S\n" +
                "To the D to the E to the L I C I O U S\n" +
                "To the D to the E to the, to the, to the, to the, to the...";
    }

}