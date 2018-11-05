package hi;

import java.util.Scanner;

public class Character {
	Boolean godMode = false;
	int def;
	int atk;
	int exp = 0;
	int lv = 1;
	int health = 100;
	int gold = 250;
	String name;
	String fullIvnt;
	String[] display = new String[2];
	String[] choices = new String[9];
	String[] iNames = new String[6];
	String[] ivntFunctions = new String[] {
			"Weapon", "Armor", "Item 1", "Item 2", "Passive Item"
	};
	Boolean itemInSlot;
	Equipment equipment;
	Boolean check = false;
	Equipment[] inventory = new Equipment[5];
	Scanner input = new Scanner(System.in);
	Prompt prompt = new Prompt(this);

	Character(Equipment equipment) {
		for (int b = 1; b < 4; b++) {
			this.inventory[b] = null;
		}
		this.inventory[0] = equipment;
		this.atk = equipment.getStat();
		this.iNames[0] = equipment.getName();
		this.equipment = equipment;
		this.iNames[5] = "exit";
		this.doGodMode();
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
	
	public void doGodMode() {
		if(this.godMode) {
			this.atk = 1000000;
			this.def = 1000000;
			this.gold = 1000000;
			this.health = 10000000;
		}
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
			System.out.println(itemClass + ": " + this.iNames[whichSlot]);
			this.choices[choiceNum] = this.iNames[whichSlot];
		} else {
			System.out.println(itemClass + ": none");
		}
	}
	
	public void checkStatus() {
		System.out.println("health: " + this.getHealth() + "\nAtk: " + this.getAtk() + "\nDef: " + this.getDef() + "\nGold: " + this.getGold());
		for(int c = 0; c < this.inventory.length; c++) {
			this.openSlot(c+4, c, this.ivntFunctions[c]);
		}
		this.display[0] = "exit";
		this.display[1] = "type an item's name to get that item's info";
		this.check = false;
		while(!this.check) {
			this.prompt.usePrompt(this.iNames, this.display);
			for(int d = 0; d < this.inventory.length; d++) {
				if(this.prompt.getPChoice().equalsIgnoreCase(this.choices[d+4])) {
					System.out.println(this.inventory[d].getText());
				}
			}
			if(this.prompt.getPChoice().equalsIgnoreCase("exit")) {
				this.check = true;
			}
		}
		for(int e = this.display.length; e < this.choices.length; e++) {
			this.choices[e] = null;
		}
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

	public void Die() {
		this.health = 100;
		this.gold = 250;
		this.exp = 0;
		this.lv = 1;
		for (int f = 0; f < this.inventory.length; f++) {
			this.inventory[f] = null;
		}
		this.inventory[0] = this.equipment;
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
				this.iNames[1] = Equipment.getName();
				this.def = Equipment.getStat();
			}
		} else if (Equipment.isItem()) {
			if (Equipment.getFunction().equalsIgnoreCase("passive")) {
				if(this.inventory[4] != null) {
					this.itemInSlot = true;
					this.fullIvnt = this.inventory[4].getName();
				}else {
					this.inventory[4] = Equipment;
					this.iNames[4] = Equipment.getName();
				}
			} else {
				if (this.inventory[2] != null && this.inventory[3] != null) {
					this.itemInSlot = true;
					this.fullIvnt = this.inventory[2].getName() + " and a " + this.inventory[3].getName();
				} else if (this.inventory[2] == null) {
					this.inventory[2] = Equipment;
					this.iNames[2] = Equipment.getName();
				} else {
					this.inventory[3] = Equipment;
					this.iNames[3] = Equipment.getName();
				}
			}
		} else {
			if (this.inventory[0] != null) {
				this.itemInSlot = true;
				this.fullIvnt = this.inventory[0].getName();
			} else {
				this.inventory[0] = Equipment;
				this.iNames[0] = Equipment.getName();
				this.atk = Equipment.getStat();
			}
		}
		if (this.itemInSlot) {
			if (Equipment.isItem() && !(Equipment.isPassive())) {
				System.out.println("you already have a " + this.fullIvnt + ". Replace one?");
				this.prompt.yesNo();
				if(this.prompt.getPChoice().equalsIgnoreCase("yes")) {
					this.display[0] = this.inventory[2].getName();
					this.display[1] = this.inventory[3].getName();
					this.prompt.yesNo(this.display[0], this.display[1], "Which one?");
				}
			} else {
				System.out.println("you already have a " + this.fullIvnt + ". Replace it?");
				this.prompt.yesNo();
			}
			if(prompt.getPChoice().equalsIgnoreCase("yes")) {
				if (Equipment.isArmor()) {
					this.inventory[1] = Equipment;
					this.iNames[1] = Equipment.getName();
					this.def = Equipment.getStat();
				} else if (Equipment.isPassive()) {
					this.inventory[4] = Equipment;
					this.iNames[4] = Equipment.getName();
				} else {
					this.inventory[0] = Equipment;
					this.iNames[0] = Equipment.getName();
					this.atk = Equipment.getStat();
				}
			}
			if(this.inventory[2] != null) {
				if (this.prompt.getPChoice().equalsIgnoreCase(this.inventory[2].getName())) {
					this.inventory[2] = Equipment;
					this.iNames[2] = Equipment.getName();
				}
			}
			if(this.inventory[3] != null) {
				if(this.prompt.getPChoice().equalsIgnoreCase(this.inventory[2].getName())) {
					this.inventory[3] = Equipment;
					this.iNames[3] = Equipment.getName();
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

	public void attackPrompt() {
		System.out.println(this.name + " has: " + this.health + " health");
		if (this.inventory[2] == null) {
			System.out.println(this.name + " can attack or run away.");
			this.choices[0] = "attack";
			this.choices[1] = "run";
			this.choices[2] = null;
			this.choices[3] = null;
			prompt.usePrompt(this.choices);
		} else {
			if (this.inventory[3] == null) {
				System.out.println(this.name + " can attack, run away, or use their " + this.inventory[2].getName());
				this.choices[0] = "attack";
				this.choices[1] = "run";
				this.choices[2] = this.inventory[2].getName();
				this.choices[3] = null;
				prompt.usePrompt(this.choices);
			} else {
				System.out.println(
						this.name + " can attack, run away, use their " + this.inventory[2].getName() + ", or they can use their " + this.inventory[3].getName());
				this.choices[0] = "attack";
				this.choices[1] = "run";
				this.choices[2] = this.inventory[2].getName();
				this.choices[3] = this.inventory[3].getName();
				prompt.usePrompt(this.choices);
			}
		}
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
				((Mob)boss).itemDmg(this.inventory[w].getStat());
			} else {
				System.out.println(this.name + " throws the " + this.inventory[w].getName() + " at the " + boss.getName() + ", doing some damage");
				boss.takeDmg(this.inventory[w].getStat());
			}
		} else if (this.inventory[w].function.equals("buffAtk")) {
			System.out.println(this.name + "'s attack rises by " + this.inventory[w].getStat());
			this.updateAtk(this.inventory[2].getStat());
		} else if (this.inventory[w].function.equals("buffDef")) {
			System.out.println(this.name + "'s defense rises by " + this.inventory[w].getStat());
			this.updateDef(this.inventory[w].getStat());
		}
	}
	
	public void attack(Boss enemy) {
		this.attackPrompt();
		if (prompt.getPChoice().equalsIgnoreCase("attack")) {
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
				enemy.takeDmg(this.atk);
			}
		} else if (prompt.getPChoice().equalsIgnoreCase("run")) {
			double gotHit = 6 * Math.random();
			if(this.godMode) {
				gotHit += 5.1;
			}
			if (gotHit <= 5) {
				System.out.println(this.name + " failed to get away");
			} else {
				System.out.println(this.name + " successfully ran away");
				return;
			}
		} else if (prompt.getPChoice().equalsIgnoreCase(this.inventory[2].getName())) {
			this.itemEffect(enemy, 2);
		} else {
			this.itemEffect(enemy, 3);
		}
		this.doGodMode();
	}
}