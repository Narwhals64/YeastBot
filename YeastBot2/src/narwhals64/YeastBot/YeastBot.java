package narwhals64.YeastBot;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

import javax.security.auth.login.LoginException;

import narwhals64.YeastBot.Commands.*;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;

public class YeastBot {
	
	public static String prefix = ",";
	
	public static String profilesPath = "C:\\Users\\Ethan Rao\\Desktop\\Yeast Bot\\profiles\\";
	
	public static ArrayList<YeastBotProfile> profiles = new ArrayList<>();
	
	public static void main(String[] args) throws LoginException {
		
		JDABuilder builder = new JDABuilder();
		
		builder.setToken("MjQzMDYxNzUwMTEzNjMyMjU4.XiqTXg.iX0FvigyckBZNHB4OfRQvX8ZiwM");
		
		builder.setStatus(OnlineStatus.ONLINE);
		
		builder.addEventListeners(new Clear());
		builder.addEventListeners(new Info());
		builder.addEventListeners(new Question());
		builder.addEventListeners(new Say());
		
		// YeastBA
		builder.addEventListeners(new Inv());
		builder.addEventListeners(new Shop());
		
		builder.build();
		
		System.out.println("Loading Yeast Bot Profiles.");
	}
	
	public static void loadProfiles() {
		File profilesFolder = new File(profilesPath);
		for (File f : profilesFolder.listFiles()) {
			if (f.isFile()) {
				profiles.add(new YeastBotProfile(f.getName().substring(0,f.getName().length() - 4)));
			}
		}
	}
	
	public static YeastBotProfile getProfile(String id) {
		for (YeastBotProfile prof : profiles)
			if (id.equals(prof.getId()))
				return prof;
		createProfile(id);
		return profiles.get(profiles.size()-1);
	}
	
	public static void createProfile(String id) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(YeastBot.profilesPath + id + ".txt"));
			bw.write("");
			bw.close();
			
		} catch (Exception e) {

		}
		profiles.add(new YeastBotProfile(id));
	}
}
