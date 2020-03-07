package narwhals64.YeastBot.YeastBA;

import narwhals64.YeastBot.YeastBotProfile;
import narwhals64.YeastBot.Items.Item;
import narwhals64.YeastBot.Items.Weapons.Weapon;

/**
 * This is both used in-game operators.
 * @author Ethan Rao
 *
 */
public class Operator {	
	private YeastBotProfile controller;
	
	private Weapon rightWeapon;
	private Weapon leftWeapon;
	
	public Operator() {
		
	}
	public Operator(String rightWeapon, String leftWeapon, String helmet, String torso, String shoulders, String leggings) {
		this.rightWeapon = (Weapon)Item.fetchItem(Integer.parseInt(rightWeapon));
		//this.leftWeapon = (Weapon)Item.fetchItem(Integer.parseInt(leftWeapon));
	}
	
	public void makeDefaultOperator() {
		setRightWeapon(1000);
	}
	
	public void setRightWeapon(int index) {
		rightWeapon = (Weapon)Item.fetchItem(index);
	}
	public void setRightWeapon(String index) {
		rightWeapon = (Weapon)Item.fetchItem(Integer.parseInt(index));
	}
	public Weapon getRightWeapon() {
		return rightWeapon;
	}
}
