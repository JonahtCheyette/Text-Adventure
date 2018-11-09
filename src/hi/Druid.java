package hi;

public class Druid extends Adventurer{
	Druid(String name,int health, int exp, int lv, int gold, Equipment[] inventory){
		super(name, health, exp, lv, gold, inventory);
	}
	
	Druid(){
		
	}
	
	public static void becomeClass(String name) {
		System.out.println(name + " is now a Druid!\nPotions are more effective, but your stats are lower.");
	}
	
	public void itemEffect(Boss boss, int w){
		System.out.println(this.name + " uses the " + this.inventory[w].getName());
		if (this.inventory[w].function.equals("Healing")) {
			System.out.println(this.name + " heals " + this.inventory[w].getStat() + " health points");
			this.heal((int) (this.inventory[w].getStat()*1.5));
		} else if (this.inventory[w].function.equals("Damage")) {
			if(boss instanceof Mob && ((Mob) boss).getCount() > 1) {
				System.out.println(this.name + " throws the " + this.inventory[2].getName() + " at the group of " + boss.getName()
				+ ", doing some damage to all of them");
				((Mob)boss).itemDmg((int)(this.inventory[w].getStat()*1.5));
			} else {
				System.out.println(this.name + " throws the " + this.inventory[w].getName() + " at the " + boss.getName() + ", doing some damage");
				boss.takeDmg((int)(this.inventory[w].getStat()*1.5));
			}
		} else if (this.inventory[w].function.equals("buffAtk")) {
			System.out.println(this.name + "'s attack rises by " + this.inventory[w].getStat());
			this.updateAtk((int)(this.inventory[2].getStat()*1.5));
		} else if (this.inventory[w].function.equals("buffDef")) {
			System.out.println(this.name + "'s defense rises by " + this.inventory[w].getStat());
			this.updateDef((int)(this.inventory[w].getStat()*1.5));
		}
	}
	
	public void gainExp(int gain, int numOfTimes) {
		if(this.mExpTracker == 0) {
			this.oldLv = this.lv;
			this.oldExp = this.exp;
		}
		this.mExpTracker ++;
		this.exp += gain;
		this.lv = (int) (1.5 * Math.sqrt(this.exp));
		if(this.mExpTracker == numOfTimes) {
			this.lvStatChange = (int) ((this.lv * 1.5) - 2);
			System.out.println("You have gained " + (this.exp - this.oldExp) + " Exp. Your level is now " + this.lv);
			this.updateAtk((this.lv - this.oldLv) * 2);
			this.updateDef((this.lv - this.oldLv) * 2);
			System.out.println("your Atk is now " + this.atk + "\nyour Def is now " + this.def);
			this.mExpTracker = 0;
		}
	}
}
