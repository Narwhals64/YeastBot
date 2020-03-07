package narwhals64.YeastBot.Items.Containers;

import java.util.ArrayList;

import narwhals64.YeastBot.Items.Item;
import narwhals64.YeastBot.Items.Template;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class TemplateHolder extends Item {
	private ArrayList<Template> templates;
	
	public TemplateHolder() {
		super(1,"Template Holder","A container for Operator Templates.","");
		templates = new ArrayList<>();
	}
	
	public Template getTemplate(int n) {
		return templates.get(n);
	}


	@Override
	public void view(GuildMessageReceivedEvent event, int level) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (args.length == 2) {
			EmbedBuilder embed = new EmbedBuilder();
			
			embed.setColor(0xC70000);
			
			embed.setTitle("Container Information");
			embed.setDescription("Extra Parameters:\nn - View Template details.");
			
			embed.addField(getName(),getDesc(),false);
			if (!getLore().equals("")) {
				embed.addField("",getLore(),false);
			}
			embed.addField("Amount Owned: " + getAmt(),"",false);
			embed.addField("Item ID: " + getId(),"",false);
			
			embed.setFooter("YeastBot",event.getAuthor().getAvatarUrl());
			
			event.getChannel().sendMessage(embed.build()).queue();
		}
		
		else {
			if (args[2].equals("1")) {
				//viewAttacks(event);
			}
		}
	}
}
