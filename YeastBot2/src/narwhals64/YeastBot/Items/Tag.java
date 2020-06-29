package narwhals64.YeastBot.Items;

import narwhals64.YeastBot.YeastBotProfile;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Tag extends Item {
	private String tagData;
	
	public Tag() {
		super(11,"Tag","A random string of characters.","");
		tagData = "-";
		setTitle("Tag");
		setUnique();
	}
	public Tag(String tag) {
		super(11,"Tag - " + tag,"A random string of characters.","");
		tagData = tag;
		setTitle("Tag");
		setUnique();
	}
	
	public String getVariableData() {
		return super.getVariableData() + ":" + tagData;
	}
	
	public void setVariableData(String data) {
		super.setVariableData(data); // super method takes care of amount already
		String[] sepData = YeastBotProfile.separateWithParams(data);
		tagData = sepData[1]; // tag data is the next on the list
		setName("Tag - " + tagData);
	}
	
	@Override
	public void view(GuildMessageReceivedEvent event, int level) {
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if (args.length - 1 == level) { // if the level "matches" the args length (no extra parameters were entered), run the following code
			event.getChannel().sendTyping().queue();
		
			EmbedBuilder embed = new EmbedBuilder();
			
			embed.setColor(0x42FFF);
			
			embed.setTitle(getTitle() + " Information");
			embed.setDescription("This item has no extra parameters.");
				
			embed.addField(getName(),getDesc(),false);
			embed.addField("Unique Tag:",tagData,false);
			embed.addField("Item ID: " + getId(),"",false);
			
			embed.setFooter("YeastBot",event.getGuild().getMemberById(getOwner().getId()).getUser().getAvatarUrl());
			
			event.getChannel().sendMessage(embed.build()).queue();
			
			embed.clear();
		}
	}
}
