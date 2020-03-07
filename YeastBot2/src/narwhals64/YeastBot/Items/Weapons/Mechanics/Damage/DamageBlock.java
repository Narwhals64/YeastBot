package narwhals64.YeastBot.Items.Weapons.Mechanics.Damage;

public class DamageBlock {
	/**
	 * The type of damage to be dealt.
	 * Terminate - used for tick queues.  If this damage is dealt, then terminate the attack.
	 * @author Ethan Rao
	 *
	 */
	public enum Damage {
		SLASH, PEN, BLUNT, TERMINATE
	}
	private int amount;
	private Damage type;
	
	public DamageBlock() {
		
	}
	public DamageBlock(int amount, Damage type) {
		this.amount = amount;
		this.type = type;
	}
	
	public void setValues(int amount, Damage type) {
		this.amount = amount;
		this.type = type;
	}
	
	public int getAmount() {
		return amount;
	}
	public Damage getType() {
		return type;
	}
	
}
