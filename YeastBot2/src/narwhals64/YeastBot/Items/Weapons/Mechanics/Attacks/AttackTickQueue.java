package narwhals64.YeastBot.Items.Weapons.Mechanics.Attacks;

import narwhals64.YeastBot.Items.Weapons.Mechanics.Damage.DamageBlock;

public class AttackTickQueue {
	private AttackTick[] queue;
	private int size;
	
	public AttackTickQueue() {
		queue = new AttackTick[0];
		size = 0;
	}
	
	private void addCapacity() {
		AttackTick[] newQueue = new AttackTick[size + 1];
		for (int i = 0 ; i < size ; i++) {
			newQueue[i] = queue[i];
		}
		size++;
		queue = newQueue;
	}
	
	public void addTick(String s0, String s1, String s2, String s3, String s4, String s5, String s6, String s7, String s8, int attackSpace, DamageBlock db, AttackTick.Condition condition) {
		addCapacity();
		queue[size-1] = new AttackTick(s0,s1,s2,s3,s4,s5,s6,s7,s8,attackSpace,db,condition);
	}
	public void addTick(AttackTick at) {
		addCapacity();
		queue[size-1] = at;
	}
	
	public AttackTick getTick(int n) {
		if (n >= queue.length)
			return null;
		return queue[n];
	}
}
