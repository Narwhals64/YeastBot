package narwhals64.YeastBot.Items.Weapons;

import narwhals64.YeastBot.Items.Item;
import narwhals64.YeastBot.Items.Weapons.Mechanics.Attacks.AttackTickQueue;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public abstract class Weapon extends Item {
	public static final int AMOUNT_OF_ATTACKS = 5;
	private AttackTickQueue[] attacks;
	
	private String availableAttacks = "0 - Diagonal Slash\n"
			+ "1 - Downwards Cut\n"
			+ "2 - Uppercut\n"
			+ "3 - Charged Left Diagonal Slash\n"
			+ "4 - Charged Left Sweep\n"
			+ "5 - Slam\n"
			+ "6 - Stab\n"
			+ "7 - Lunge\n";
	
	
	public Weapon(int id, String name, String desc, String lore) {
		super(id,name,desc,lore);
		setTitle("Weapon");
		attacks = new AttackTickQueue[AMOUNT_OF_ATTACKS];
	}
	
	public void setAttack(int attackIndex, AttackTickQueue newAttack) {
		attacks[attackIndex] = newAttack;
	}
	
	@Override
	public void view(GuildMessageReceivedEvent event, int level) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		level++;
		
		if (args.length == level) { // if the level "matches" the args length (no extra parameters were entered), run the following code
			EmbedBuilder embed = new EmbedBuilder();
			
			embed.setColor(0xC70000);
			
			embed.setTitle(getTitle() + " Information");
			embed.setDescription("Extra Parameters:\n1 - Check Attacks.");
			
			embed.addField(getName(),getDesc(),false);
			if (!getLore().equals("")) {
				embed.addField("",getLore(),false);
			}
			embed.addField("Amount Owned: " + getAmt(),"",false);
			embed.addField("Item ID: " + getId(),"",false);
			
			embed.setFooter("YeastBot",event.getAuthor().getAvatarUrl());
			
			event.getChannel().sendMessage(embed.build()).queue();
		}
		
		else { // level + 2 is the extra parameter within the array
			if (args[level].equals("1")) {
				viewAttacks(event, level + 1);
			}
		}
	}
	public void viewAttacks(GuildMessageReceivedEvent event, int level) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (args.length == level + 2) {
			EmbedBuilder embed = new EmbedBuilder();
			
			embed.setColor(0xC70000);
			
			embed.setTitle("Weapon Attacks Information");
			embed.setDescription("Extra Parameters:\nn - Check Attack in greater detail.\nNote: All Attacks displayed; Un-spoilered Attacks are innately available.");
			
			embed.addField("",availableAttacks,false);
			
			embed.setFooter("YeastBot",event.getAuthor().getAvatarUrl());
			
			event.getChannel().sendMessage(embed.build()).queue();
		}
		else { // level + 2 is the extra parameter
			
		}
	}
}
