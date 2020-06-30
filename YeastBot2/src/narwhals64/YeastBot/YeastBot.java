package narwhals64.YeastBot;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import javax.security.auth.login.LoginException;

import narwhals64.YeastBot.CardGames.GamePlayer;
import narwhals64.YeastBot.Commands.*;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class YeastBot {
	
	public static String prefix = ",";
	
	public static String profilesPath = "C:\\Users\\Ethan Rao\\Desktop\\Yeast Bot\\profiles\\";
	public static String guildsPath = "C:\\Users\\Ethan Rao\\Desktop\\Yeast Bot\\profiles\\";
	
	public static ArrayList<YeastBotProfile> profiles = new ArrayList<>();
	public static ArrayList<GuildProfile> guilds = new ArrayList<>();

	public static ArrayList<GameInstance> gameInstances;
	public static HashMap<String, Integer> gameScopes;

	public static boolean useYeastBotProfiles = true;
	
	public static void main(String[] args) throws LoginException {

		
		JDABuilder builder = new JDABuilder();


		builder.setToken("MjQzMDYxNzUwMTEzNjMyMjU4.XiqTXg.iX0FvigyckBZNHB4OfRQvX8ZiwM");
		
		builder.setStatus(OnlineStatus.ONLINE);

		builder.addEventListeners(new BasicText()); // command-less
		
		builder.addEventListeners(new Clear());
		builder.addEventListeners(new Info());
		builder.addEventListeners(new Question());
		builder.addEventListeners(new Say());
		builder.addEventListeners(new SetPrefix());

        builder.addEventListeners(new Game());

        builder.addEventListeners(new Inv());
		//builder.addEventListeners(new Shop());


		builder.addEventListeners(new Ferg());


		//builder.addEventListeners(new Fish());


		//builder.addEventListeners(new Kill());


		
		builder.build();



		gameInstances = new ArrayList<>();
		gameScopes = new HashMap<>();

	}


	public static YeastBotProfile getProfile(String id) {
	    if (useYeastBotProfiles) {
            for (YeastBotProfile prof : profiles)
                if (id.equals(prof.getId()))
                    return prof;
			profiles.add(new YeastBotProfile(id));
			return profiles.get(profiles.size()-1);
        }
        return null;
    }

    public static GuildProfile getGuild(String id) {
		for (GuildProfile gp : guilds) {
			if (gp.getId().equals(id)) {
				return gp;
			}
		}
		guilds.add(new GuildProfile(id));
		return getGuild(id);
	}
	public static GuildProfile getGuild(GuildMessageReceivedEvent event) {
		return getGuild(event.getGuild().getId());
	}

    public static String getPrefix(GuildMessageReceivedEvent event) {
		return getGuild(event).getPrefix();
	}



}
