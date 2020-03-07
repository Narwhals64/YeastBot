package narwhals64.YeastBot.Items;

import narwhals64.YeastBot.YeastBotProfile;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ThreeLetterTagGacha extends UsableItem {

	public ThreeLetterTagGacha() {
		super(12, "Three-Letter Tag Gacha", "An item one may use to acquire a random Tag, comprised of only three letters.", "");
		// TODO Auto-generated constructor stub
	}
	
	public void use(GuildMessageReceivedEvent event) {
		String newGacha = "";
		newGacha += (char)((int)(Math.random() * 26.0) + 65);
		newGacha += (char)((int)(Math.random() * 26.0) + 65);
		newGacha += (char)((int)(Math.random() * 26.0) + 65);
		Tag newTag = new Tag(newGacha);
		YeastBotProfile prof = getOwner();
		prof.addItem(newTag);
		prof.findItem(12).incrementAmt(-1);
		prof.save();
	}
}
