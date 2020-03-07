package narwhals64.YeastBot.Items.Weapons.Mechanics.Attacks;

import narwhals64.YeastBot.Items.Weapons.Mechanics.Damage.DamageBlock;

/**
 * An attack tick contains the info of the exact location a weapon is in during the tick of the attack.
 * This location is used to determine if an attack from an enemy facing the same direction will hit or be blocked
 * @author Ethan Rao
 *
 */
public class AttackTick {
	private String[] spaces;
	private DamageBlock db;
	/**
	 * Stance - the first tick
	 * NextTick - will activate on the tick after the last AttackTick
	 * LastTickHit - will activate with the last tick, as long as the last tick hit.  Otherwise, skip over and continue.
	 * LastTickMissed - will activate with the last tick, as long as the last tick missed.  Otherwise, skip over and continue.
	 * WithLastTick - will activate with the last tick, regardless of any other circumstance
	 * @author Ethan Rao
	 *
	 */
	public static enum Condition {
		Stance, NextTick, LastTickHit, LastTickMissed, WithLastTick
	}
	private int attackSpace; // default 0
	private Condition condition;
	
	public AttackTick(String s0, String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, int attackSpace, DamageBlock db, Condition condition) {
		spaces = new String[9];
		spaces[0] = s0;
		spaces[1] = s1;
		spaces[2] = s2;
		spaces[3] = s3;
		spaces[4] = s4;
		spaces[5] = s5;
		spaces[6] = s6;
		spaces[7] = s7;
		spaces[8] = s8;
		this.db = db;
		this.condition = condition;
	}
	

	/**
	 * Using this requires the user-end input of n + 1.  space 5 = spaces[4]
	 * @param n
	 * @return
	 */
	public String getSpace(int n) {
		return spaces[n - 1];
	}
	
	public int getAttackSpace() {
		return attackSpace;
	}
	
	public DamageBlock getDamageBlock() {
		return db;
	}
	
	public Condition getCondition() {
		return condition;
	}
}
