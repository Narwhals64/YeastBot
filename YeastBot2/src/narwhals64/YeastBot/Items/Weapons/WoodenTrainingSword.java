package narwhals64.YeastBot.Items.Weapons;

import narwhals64.YeastBot.Items.Weapons.Mechanics.Attacks.AttackTick;
import narwhals64.YeastBot.Items.Weapons.Mechanics.Attacks.AttackTickQueue;
import narwhals64.YeastBot.Items.Weapons.Mechanics.Damage.DamageBlock;
import narwhals64.YeastBot.Items.Weapons.Mechanics.Damage.DamageBlock.Damage;

public class WoodenTrainingSword extends Weapon {
	public WoodenTrainingSword() {
		super(1000,"Wooden Training Sword","A blunt wooden sword used for training.\nNot badly made, but will likely give the user splinters if used to fend off attacks.\nThe pointy tip may be able to jab.  Or maybe not.","");
		AttackTickQueue a0 = new AttackTickQueue();
		a0.addTick("","H","","X","LA","LA","","LL","",0,null,AttackTick.Condition.Stance);
		a0.addTick("X","H","","RA","LA","LA","","LL","",0,null,AttackTick.Condition.NextTick);
		a0.addTick("RA","H","","RA","LA","LA","","LL","",0,null,AttackTick.Condition.NextTick);
		a0.addTick("","H","","RA","T","LA","RL","LL","",3,new DamageBlock(2,Damage.BLUNT),AttackTick.Condition.NextTick);
		a0.addTick("","H","","RA","T","LA","RL","LL","",3,new DamageBlock(1,Damage.SLASH),AttackTick.Condition.LastTickHit);
		a0.addTick("","H","","RA","T","LA","RL","LL","",0,null,AttackTick.Condition.LastTickMissed);
		a0.addTick("","H","","RA","T","LA","RL","LL","",6,new DamageBlock(3,Damage.BLUNT),AttackTick.Condition.NextTick);
		a0.addTick("","H","","RA","T","LA","RL","LL","",6,new DamageBlock(1,Damage.SLASH),AttackTick.Condition.LastTickHit);
		a0.addTick("","H","","RA","T","LA","RL","LL","",5,new DamageBlock(3,Damage.BLUNT),AttackTick.Condition.LastTickMissed);
		a0.addTick("","H","","RA","T","LA","RL","LL","",5,new DamageBlock(1,Damage.SLASH),AttackTick.Condition.LastTickHit);
		setAttack(0,a0);
	}
}
