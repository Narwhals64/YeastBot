package narwhals64.YeastBot.Main;

import java.util.ArrayList;

public abstract class Match {
	private String matchType;
	private int matchId;
	
	private ArrayList<Operator> purpleOps;
	private int purpleTeamOperatorCount;
	private int purpleTeamCurrentOperator;
	
	private ArrayList<Operator> orangeOps;
	private int orangeTeamOperatorCount;
	private int orangeTeamCurrentOperator;
	
	public Match() {
		
	}
}
