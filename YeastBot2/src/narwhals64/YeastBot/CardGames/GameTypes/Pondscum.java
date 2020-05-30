package narwhals64.YeastBot.CardGames.GameTypes;

import narwhals64.YeastBot.CardGames.GamePlayer;
import narwhals64.YeastBot.CardGames.PlayerTypes.PondscumPlayer;
import narwhals64.YeastBot.GameInstance;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.ArrayList;

public class Pondscum extends GameInstance {
    private PondscumPlayer host;
    private ArrayList<PondscumPlayer> players; // at first, this is not ordered.  After the first game, it is ordered.
    private ArrayList<PondscumPlayer> waitingList; // waiting list for new players.  Each new player pushes the rest of the players up, thus becoming the next Pondscum.
    private boolean allPlayersNormal; // for the first game, all players are considered normal.

    Guild guild;
    TextChannel channel;

    private int curPlayerIndex;

    public Pondscum(GuildMessageReceivedEvent event) {
        super("Pondscum Game #" + (GAME_INDEX_COUNTER+1));

        host = null;
        players = new ArrayList<>();
        waitingList = new ArrayList<>();
        allPlayersNormal = true;

        guild = event.getGuild();
        channel = event.getChannel();

        curPlayerIndex = 0;
    }

    public Pondscum(GuildMessageReceivedEvent event, User creator) {
        super("Pondscum Game #" + (GAME_INDEX_COUNTER+1));

        host = new PondscumPlayer(creator.getId());
        players = new ArrayList<>();
        waitingList = new ArrayList<>();
        allPlayersNormal = true;

        guild = event.getGuild();
        channel = event.getChannel();

        curPlayerIndex = 0;
    }

    /**
     * Add a player to the waiting list.
     * @param newPlayer PondscumPlayer to be added.
     */
    public void addPlayer(PondscumPlayer newPlayer) {
        waitingList.add(newPlayer);
    }

    /**
     * Create a player from a Discord user, then add it to the waiting list.
     * Do not add if there is already a player with that ID.
     * @param newPlayer
     */
    public void addPlayer(User newPlayer) {
        boolean doNotAdd = false;

        if (host.getId().equals(newPlayer.getId()))
            doNotAdd = true;

        for (GamePlayer gp : players)
            if (gp.getId().equals(newPlayer.getId()))
                doNotAdd = true;

        for (GamePlayer gp : waitingList)
            if (gp.getId().equals(newPlayer.getId()))
                doNotAdd = true;

        if (!doNotAdd) {
            addPlayer(new PondscumPlayer(newPlayer.getId().toString()));
            channel.sendMessage("A player was successfully added to the Waiting List!  You will join in the next round.").queue();
        }
        else {
            channel.sendMessage("The player could not be added to the Waiting List.  You may already be in the game!").queue();
        }
    }

    /**
     * Insert all players in the waiting list into the player list.
     * Each player is moved to the bottom of the player list, making them automatically Pondscum.
     */
    public void joinPlayers() {
        int wlsize = waitingList.size();
        for (int i = 0 ; i < wlsize ; i++) {
            players.add(0,waitingList.get(0)); // Move the first person from the waiting list to the bottom of the players list, thus making them pondscum.
            if (players.size() == 1) // if there's one player then make him host
                host = players.get(0);
            waitingList.remove(0); // now remove the player from the waitingList since they are in the game.
        }
    }

    public void initializeRound() {
        /*
        for (PondscumPlayer player : players) {
            Member member = guild.getMemberById(player.getId());

            guild.createTextChannel("pondscum-" + player.getId()).
                    addPermissionOverride(guild.getRoleById(240614349247479819L),null, Collections.singleton(Permission.MESSAGE_READ)).  // Set @everyone to not be able to read messages
                    addPermissionOverride(member, Collections.singleton(Permission.MESSAGE_READ),null).                                  // Set the given player to read the new channel
                    addPermissionOverride(member, Collections.singleton(Permission.MESSAGE_WRITE),null).queue();                         // Set the given player to write to the new channel

        }
        */

        joinPlayers();

    }


    public String displayCurrentState() {
        String output = "";

        output += "The host is " + guild.getMemberById(host.getId()).getNickname() + ".\n";

        output += "Other players: ";
        for (GamePlayer gp : players) {
            output += guild.getMemberById(gp.getId()).getNickname() + " ";
        }
        output += "\n";

        output += "In the waiting list: ";
        for (GamePlayer gp : waitingList) {
            output += guild.getMemberById(gp.getId()).getNickname() + " ";
        }
        output += "\n";

        return output;

    }


}
