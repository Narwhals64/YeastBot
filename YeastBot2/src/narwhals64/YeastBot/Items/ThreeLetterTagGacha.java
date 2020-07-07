package narwhals64.YeastBot.Items;

import narwhals64.YeastBot.Items.Containers.ItemDirectory;
import narwhals64.YeastBot.YeastBotProfile;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ThreeLetterTagGacha extends UsableItem {

	public ThreeLetterTagGacha() {
		super(110, "Three-Letter Tag Gacha", "An item one may use to acquire a random Tag, comprised of only three letters.", "");
		setUnique();
	}
	
	public void use(GuildMessageReceivedEvent event) {
		String newGacha = "";
		newGacha += (char)((int)(Math.random() * 26.0) + 65);
		newGacha += (char)((int)(Math.random() * 26.0) + 65);
		newGacha += (char)((int)(Math.random() * 26.0) + 65);
		Tag newTag = new Tag(newGacha);
		event.getChannel().sendMessage("You received the following Tag: " + newGacha).queue();
		YeastBotProfile prof = getOwner();
		((ItemDirectory)prof.findItem(1)).addItem(newTag); // add tag directly to tag bag
		incrementAmt(-1);
		prof.clean();
		prof.save();
	}

}
