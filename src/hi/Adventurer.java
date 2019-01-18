package hi;

import java.util.Arrays;
import java.util.Scanner;

public class Adventurer {
	Boolean godMode = false;
	int def;
	int atk;
	int oldExp;
	int oldLv;
	int lvStatChange = 0;
	int mExpTracker = 0;
	int exp = 0;
	int lv = 1;
	int health = 100;
	int gold = 250;
	int runThrough = 0;
	int[] whichAreFull;
	String name;
	String fullIvnt;
	String[] display = new String[2];
	String[] choices = new String[9];
	String[] iNames = new String[6];
	String[] ivntFunctions = new String[] {
			"Weapon", "Armor", "Item 1", "Item 2", "Passive Item"
	};
	Boolean itemInSlot;
	Boolean checkRan = false;
	Boolean check = false;
	Equipment[] inventory = new Equipment[5];
	Scanner input = new Scanner(System.in);

	Adventurer(String name,int health, int exp, int lv, int gold, Equipment[] inventory){
		this.name = name;
		this.health = health;
		this.exp = exp;
		this.lv = lv;
		this.gold = gold;
		this.inventory = inventory;
	}
	
	Adventurer() {
		Arrays.fill(this.inventory, null);
		this.atk = 0;
		this.iNames[5] = "exit";
		this.doGodMode();
		this.inventory[0] = new Equipment("weapon", "Wood Sword", 30, 25, 0, "A wooden sword. More suited for practice bouts than actual combat.");
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDef() {
		return this.def;
	}

	public int getAtk() {
		return this.atk;
	}

	public int getExp() {
		return this.exp;
	}

	public int getLV() {
		return this.lv;
	}
	
	public Equipment[] getInventory() {
		return this.inventory;
	}

	public Equipment getWeapon() {
		return this.inventory[0];
	}

	public Equipment getArmor() {
		return this.inventory[1];
	}

	public Equipment getItem1() {
		return this.inventory[2];
	}

	public Equipment getItem2() {
		return this.inventory[3];
	}

	public Equipment getPassiveItem() {
		return this.inventory[4];
	}

	public int getHealth() {
		return this.health;
	}

	public int getGold() {
		return this.gold;
	}

	public String getName() {
		return this.name;
	}

	public Boolean getItemInSlot() {
		return this.itemInSlot;
	}
	
	public Boolean getCheckRan() {
		return this.checkRan;
	}
	
	public String[] getInventoryNames() {
		this.runThrough = 0;
		for(int i = 0; i < this.inventory.length;i++) {
			if(this.inventory[i] != null) {
				this.iNames[this.runThrough] = this.inventory[i].getName();
				this.runThrough++;
			}
		}
		this.runThrough = 0;
		return iNames;
	}
	
	public void doGodMode() {
		if(this.godMode) {
			this.atk = 1000000;
			this.def = 1000000;
			this.gold = 1000000;
			this.health = 10000000;
		}
	}
	
	public static void becomeClass(String name) {
		System.out.println(name + " is now a Adventurer!\nYour stats and ability to use potions are about average now");
	}
	
	public Boolean isSlotFull(int whichSlot) {
		if(this.inventory[whichSlot] == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public void openSlot(int choiceNum, int whichSlot, String itemClass) {
		if (this.isSlotFull(whichSlot)) {
			System.out.println(itemClass + ": " + this.inventory[whichSlot].getName());
			this.choices[choiceNum] = this.inventory[whichSlot].getName();
		} else {
			System.out.println(itemClass + ": none");
		}
	}
	
	public void checkStatus() {
		System.out.println("Level: " + this.getLV() + "\nHealth: " + this.getHealth() + "\nAtk: " + this.getAtk() + "\nDef: " + this.getDef() + "\nGold: " + this.getGold());
		for(int c = 0; c < this.inventory.length; c++) {
			this.openSlot(c+4, c, this.ivntFunctions[c]);
		}
		this.display[0] = "exit";
		this.display[1] = "type an item's name to get that item's info";
		this.check = false;
		while(!this.check) {
			Prompt.usePrompt(this,this.getInventoryNames(), this.display);
			for(int d = 0; d < this.inventory.length; d++) {
				if(Prompt.checkPChoice(false, this.choices[d+4])) {
					System.out.println(this.inventory[d].getText());
				}
			}
			if(Prompt.checkPChoice(false,"exit")) {
				return;
			}
		}
		Arrays.fill(this.choices, this.display.length, this.choices.length, null);
		this.doGodMode();
	}

	public void heal(int healBy) {
		this.health += healBy;
		this.doGodMode();
	}

	public void rest() {
		if (this.health < 100) {
			this.health = 100;
		}
		this.doGodMode();
	}

	public void Die(Equipment equipment) {
		this.health = 100;
		this.gold = 250;
		this.exp = 0;
		this.lv = 1;
		Arrays.fill(this.inventory, null);
		this.inventory[0] = equipment;
	}

	public void takeDmg(int Dmg) {
		if (!(Dmg - this.def <= 0)) {
			this.health -= (Dmg - this.def);
		}
		this.doGodMode();
	}

	public void addItemToInvt(Equipment Equipment) {
		this.doGodMode();
		this.itemInSlot = false;
		if (Equipment.isArmor()) {
			if (this.inventory[1] != null) {
				this.itemInSlot = true;
				this.fullIvnt = this.inventory[1].getName();
			} else {
				this.inventory[1] = Equipment;
				this.def = Equipment.getStat() + this.lvStatChange;
			}
		} else if (Equipment.isItem()) {
			if (Equipment.isPassive()) {
				if(this.inventory[4] != null) {
					this.itemInSlot = true;
					this.fullIvnt = this.inventory[4].getName();
				}else {
					this.inventory[4] = Equipment;
				}
			} else {
				if (this.inventory[2] != null && this.inventory[3] != null) {
					this.itemInSlot = true;
					this.fullIvnt = this.inventory[2].getName() + " and a " + this.inventory[3].getName();
				} else if (this.inventory[2] == null) {
					this.inventory[2] = Equipment;
				} else {
					this.inventory[3] = Equipment;
				}
			}
		} else {
			if (this.inventory[0] != null) {
				this.itemInSlot = true;
				this.fullIvnt = this.inventory[0].getName();
			} else {
				this.inventory[0] = Equipment;
				this.atk = Equipment.getStat() + this.lvStatChange;
			}
		}
		if (this.itemInSlot) {
			if (Equipment.isItem() && !(Equipment.isPassive())) {
				System.out.println("you already have a " + this.fullIvnt + ". Replace one?");
				Prompt.yesNo();
				if(Prompt.getPChoice().equalsIgnoreCase("yes")) {
					this.display[0] = this.inventory[2].getName();
					this.display[1] = this.inventory[3].getName();
					Prompt.yesNo(this.display[0], this.display[1], "Which one?");
				}
			} else {
				System.out.println("you already have a " + this.fullIvnt + ". Replace it?");
				Prompt.yesNo();
			}
			if(Prompt.checkPChoice(false,"yes")) {
				if (Equipment.isArmor()) {
					this.inventory[1] = Equipment;
					this.def = Equipment.getStat() + this.lvStatChange;
				} else if (Equipment.isPassive()) {
					this.inventory[4] = Equipment;
				} else {
					this.inventory[0] = Equipment;
					this.atk = Equipment.getStat() + this.lvStatChange;
				}
			}
			if(this.inventory[2] != null) {
				if (Prompt.checkPChoice(false,this.inventory[2].getName())) {
					this.inventory[2] = Equipment;
				}
			}
			if(this.inventory[3] != null) {
				if(Prompt.checkPChoice(false,this.inventory[2].getName())) {
					this.inventory[3] = Equipment;
				}
			}
		}
		if (this.godMode) {
			this.atk = 1000000;
			this.def = 1000000;
			this.gold = 1000000;
		}
	}

	public void useItem(int which) {
		this.inventory[which] = null;
		if (this.inventory[2] == null) {
			this.inventory[2] = this.inventory[3];
			this.inventory[3] = null;
		}
		this.doGodMode();
	}

	public int calculateAtk() {
		this.atk = this.inventory[0].getStat();
		this.atk += this.lvStatChange;
		return this.atk;
	}
	
	public int calculateDef() {
		this.def = this.inventory[1].getStat();
		this.def += this.lvStatChange;
		return this.def;
	}
	
	public void updateAtk(int change) {
		this.atk += change;
		this.doGodMode();
	}

	public void updateDef(int change) {
		this.def += change;
		this.doGodMode();
	}

	public void setAtk(int set) {
		this.atk = set;
		this.doGodMode();
	}

	public void setDef(int set) {
		this.def = set;
		this.doGodMode();
	}

	public void updateGold(int bGold) {
		this.gold += bGold;
		this.doGodMode();
	}
	
	public void gainExp(int gain, int numOfTimes) {
		if(this.mExpTracker == 0) {
			this.oldLv = this.lv;
			this.oldExp = this.exp;
		}
		this.mExpTracker ++;
		this.exp += gain;
		this.lv = (int) (Math.sqrt(this.exp));
		if(this.mExpTracker == numOfTimes) {
			this.lvStatChange = (this.lv * 2) - 2;
			System.out.println("You have gained " + (this.exp - this.oldExp) + " Exp. Your level is now " + this.lv);
			this.updateAtk((this.lv - this.oldLv) * 2);
			this.updateDef((this.lv - this.oldLv) * 2);
			System.out.println("your Atk is now " + this.atk + "\nyour Def is now " + this.def);
			this.mExpTracker = 0;
		}
	}

	public void attackPrompt() {
		Arrays.fill(this.choices, null);
		System.out.println(this.name + " has " + this.health + " health");
		if (this.inventory[2] == null) {
			System.out.println(this.name + " can attack or run away.");
			this.choices[0] = "attack";
			this.choices[1] = "run";
		} else {
			if (this.inventory[3] == null) {
				System.out.println(this.name + " can attack, run away, or use their " + this.inventory[2].getName());
				this.choices[0] = "attack";
				this.choices[1] = "run";
				this.choices[2] = this.inventory[2].getName();
			} else {
				System.out.println(
						this.name + " can attack, run away, use their " + this.inventory[2].getName() + ", or they can use their " + this.inventory[3].getName());
				this.choices[0] = "attack";
				this.choices[1] = "run";
				this.choices[2] = this.inventory[2].getName();
				this.choices[3] = this.inventory[3].getName();
			}
		}
		Prompt.usePrompt(this,this.choices);
	}

	public void itemEffect(Boss boss, int w){
		System.out.println(this.name + " uses the " + this.inventory[w].getName());
		if (this.inventory[w].function.equals("Healing")) {
			System.out.println(this.name + " heals " + this.inventory[w].getStat() + " health points");
			this.heal(this.inventory[w].getStat());
		} else if (this.inventory[w].function.equals("Damage")) {
			if(boss instanceof Mob && ((Mob) boss).getCount() > 1) {
				System.out.println(this.name + " throws the " + this.inventory[2].getName() + " at the group of " + boss.getName()
				+ ", doing some damage to all of them");
			} else {
				System.out.println(this.name + " throws the " + this.inventory[w].getName() + " at the " + boss.getName() + ", doing some damage");
			}
			boss.takeDmg(this.inventory[w].getStat());
		} else if (this.inventory[w].function.equals("buffAtk")) {
			System.out.println(this.name + "'s attack rises by " + this.inventory[w].getStat());
			this.updateAtk(this.inventory[2].getStat());
		} else if (this.inventory[w].function.equals("buffDef")) {
			System.out.println(this.name + "'s defense rises by " + this.inventory[w].getStat());
			this.updateDef(this.inventory[w].getStat());
		}
	}
	
	public void attack(Boss enemy) {
		this.checkRan = false;
		this.attackPrompt();
		if (Prompt.checkPChoice(false,"attack")) {
			if(enemy instanceof Mob && ((Mob)enemy).getCount() > 1) {
				System.out.println(this.name + " attacks the group of enemies");
			} else {
				System.out.println(this.name + " attacks the " + enemy.getName());
			}
			double gotHit = 2 * Math.random();
			if(this.godMode) {
				gotHit += 1.1;
			}
			if (gotHit <= 1) {
				System.out.println("The " + enemy.getName() + " narrowly avoided the attack");
			} else {
				System.out.println("the " + enemy.getName() + " was hit by the attack");
				enemy.takeDmg(this.calculateAtk());
			}
		} else if (Prompt.checkPChoice(false,"run")) {
			double gotHit = 6 * Math.random();
			if(this.godMode) {
				gotHit += 5.1;
			}
			if (gotHit <= 5) {
				System.out.println(this.name + " failed to get away");
			} else {
				this.checkRan = true;
				System.out.println(this.name + " successfully ran away");
				return;
			}
		} else if (Prompt.checkPChoice(false,this.inventory[2].getName())) {
			this.itemEffect(enemy, 2);
		} else {
			this.itemEffect(enemy, 3);
		}
		this.doGodMode();
	}
}