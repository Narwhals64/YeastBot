package narwhals64.YeastBot.Items;

import narwhals64.YeastBot.Items.Weapons.Weapon;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Template extends Item {
	private String nick; // personal name for the template
	private String note; // personal note for the template
	
	private Weapon rightWeapon;
	private Weapon leftWeapon;
	
	public Template() {
		super(10,"Template","A save state for an ideal Operator.","");
		setUnique();
		
		nick = "";
		note = "";
		
		rightWeapon = null;
		leftWeapon = null;
	}
	
	public void view(GuildMessageReceivedEvent event) {
		event.getChannel().sendTyping().queue();
		
		EmbedBuilder embed = new EmbedBuilder();
		
		embed.setColor(0x00CC00);
		
		embed.setTitle("Template Information");
		embed.setDescription("This item has no extra parameters.");
			
		embed.addField(nick,note,false);
		
		String loadout = "";
		if (rightWeapon != null)
			loadout += rightWeapon.toString() + "\n";
		
		embed.addField("Loadout:",loadout,false);
		
		embed.setFooter("YeastBot",event.getAuthor().getAvatarUrl());
		
		event.getChannel().sendMessage(embed.build()).queue();
		
		embed.clear();
	}
}
