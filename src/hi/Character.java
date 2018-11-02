package hi;

import java.util.Scanner;

public class Character {
	int def;
	int atk;
	int whichAttacked;
	int exp = 0;
	int lv = 1;
	int health = 100;
	int gold = 5000;
	String name;
	String fullIvnt;
	String[] display = new String[2];
	String[] choices = new String[9];
	String[] iNames = new String[6];
	Boolean slotFull;
	Equipment equipment;
	Equipment[] inventory = new Equipment[5];
	Scanner input = new Scanner(System.in);
	Prompt prompt = new Prompt(this);

	Character(Equipment equipment) {
		for (int n = 1; n < 4; n++) {
			this.inventory[n] = null;
		}
		this.inventory[0] = equipment;
		this.atk = equipment.getStat();
		this.iNames[0] = equipment.getName();
		this.equipment = equipment;
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

	public Boolean getSlotFull() {
		return this.slotFull;
	}
	
	public void checkStatus() {
		System.out.println("health: " + this.getHealth() + "\nAtk: " + this.getAtk() + "\nDef: " + this.getDef() + "\nGold: " + this.getGold() + "\nWeapon: "
				+ this.getWeapon().getName());
		this.choices[4] = this.getWeapon().getName();
		if (this.getArmor() != null) {
			System.out.println("Armor: " + this.getArmor().getName());
			this.choices[5] = this.getArmor().getName();
		} else {
			System.out.println("Armor: none");
		}
		if (this.getItem1() != null) {
			System.out.println("Item 1: " + this.getItem1().getName());
			this.choices[6] = this.getItem1().getName();
		} else {
			System.out.println("Item 1: none");
		}
		if (this.getItem2() != null) {
			System.out.println("Item 2: " + this.getItem2().getName());
			this.choices[7] = this.getItem2().getName();
		} else {
			System.out.println("Item 2: none");
		}
		if (this.getPassiveItem() != null) {
			System.out.println("Passive Item: " + this.getPassiveItem().getName());
			this.choices[8] = this.getPassiveItem().getName();
		} else {
			System.out.println("Passive Item: none");
		}
		this.iNames[5] = "exit";
		this.display[0] = "exit";
		this.display[1] = "type an item's name to get that item's info";
		this.prompt.usePrompt(this.iNames, this.display);
		if(prompt.getPChoice().equalsIgnoreCase(this.choices[4])) {
			System.out.println(this.getWeapon().getText());
		} else if(prompt.getPChoice().equalsIgnoreCase(this.choices[5])) {
			System.out.println(this.getArmor().getText());
		} else if(prompt.getPChoice().equalsIgnoreCase(this.choices[6])) {
			System.out.println(this.getItem1().getText());
		} else if(prompt.getPChoice().equalsIgnoreCase(this.choices[7])) {
			System.out.println(this.getItem2().getText());
		} else if(prompt.getPChoice().equalsIgnoreCase(this.choices[8])) {
			System.out.println(this.getPassiveItem().getText());
		}
		for(int i = this.display.length; i < this.choices.length; i++) {
			this.choices[i] = null;
		}
		this.iNames[5] = null;
	}

	public void heal(int healBy) {
		this.health += healBy;
	}

	public void rest() {
		if (this.health < 100) {
			this.health = 100;
		}
	}

	public void Die() {
		this.health = 100;
		this.gold = 250;
		this.exp = 0;
		this.lv = 1;
		for (int io = 0; io < this.inventory.length; io++) {
			this.inventory[io] = null;
		}
		this.inventory[0] = this.equipment;
	}

	public void takeDmg(int Dmg) {
		if (!(Dmg - this.def <= 0)) {
			this.health -= (Dmg - this.def);
		}
	}

	public void addItemToInvt(Equipment Equipment) {
		this.slotFull = false;
		if (Equipment.isArmor()) {
			if (this.inventory[1] != null) {
				this.slotFull = true;
				this.fullIvnt = this.inventory[1].getName();
			} else {
				this.inventory[1] = Equipment;
				this.iNames[1] = Equipment.getName();
				this.def = Equipment.getStat();
			}
		} else if (Equipment.isItem()) {
			if (Equipment.getFunction().equalsIgnoreCase("passive")) {
				if(this.inventory[4] != null) {
					this.slotFull = true;
					this.fullIvnt = this.inventory[4].getName();
				}else {
					this.inventory[4] = Equipment;
					this.iNames[4] = Equipment.getName();
				}
			} else {
				if (this.inventory[2] != null && this.inventory[3] != null) {
					this.slotFull = true;
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
				this.slotFull = true;
				this.fullIvnt = this.inventory[0].getName();
			} else {
				this.inventory[0] = Equipment;
				this.iNames[0] = Equipment.getName();
				this.atk = Equipment.getStat();
			}
		}
		if (this.slotFull) {
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
	}

	public void useItem(int which) {
		this.inventory[which] = null;
		if (this.inventory[2] == null) {
			this.inventory[2] = this.inventory[3];
			this.inventory[3] = null;
		}
	}

	public void updateAtk(int change) {
		this.atk += change;
	}

	public void updateDef(int change) {
		this.def += change;
	}

	public void setAtk(int set) {
		this.atk = set;
	}

	public void setDef(int set) {
		this.def = set;
	}

	public void updateGold(int bGold) {
		this.gold += bGold;
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

	public void attack(Enemy enemy) {
		this.attackPrompt();
		if (prompt.getPChoice().equalsIgnoreCase("attack")) {
			System.out.println(this.name + " attacks the " + enemy.getName());
			double gotHit = 2 * Math.random();
			if (gotHit <= 1) {
				System.out.println("The " + enemy.getName() + " narrowly avoided the attack");
			} else {
				System.out.println("the " + enemy.getName() + " was hit by the attack");
				enemy.takeDmg(this.atk);
			}
		} else if (prompt.getPChoice().equalsIgnoreCase("run")) {
			double gotHit = 6 * Math.random();
			if (gotHit <= 5) {
				System.out.println(this.name + " failed to get away");
			} else {
				System.out.println(this.name + " successfully ran away");
				return;
			}
		} else if (prompt.getPChoice().equalsIgnoreCase(this.inventory[2].getName())) {
			System.out.println(this.name + " uses the " + this.inventory[2].getName());
			if (this.inventory[2].function.equals("Healing")) {
				System.out.println(this.name + " heals " + this.inventory[2].getStat() + " health points");
				this.heal(this.inventory[2].getStat());
			} else if (this.inventory[2].function.equals("Damage")) {
				System.out.println(this.name + " throws the " + this.inventory[2].getName() + " at the " + enemy.getName() + ", doing some damage");
				enemy.takeDmg(this.inventory[2].getStat());
			} else if (this.inventory[2].function.equals("buffAtk")) {
				System.out.println(this.name + "'s attack rises by " + this.inventory[2].getStat());
				this.updateAtk(this.inventory[2].getStat());
			} else if (this.inventory[2].function.equals("buffDef")) {
				System.out.println(this.name + "'s defense rises by " + this.inventory[2].getStat());
				this.updateDef(this.inventory[2].getStat());
			}
		} else {
			System.out.println("You use the " + this.inventory[3].getName());
			if (this.inventory[3].function.equals("Healing")) {
				System.out.println(this.name + " heals " + this.inventory[3].getStat() + " health points");
				this.heal(this.inventory[3].getStat());
			} else if (this.inventory[3].function.equals("Damage")) {
				System.out.println(this.name + " throws the " + this.inventory[3].getName() + " at the " + enemy.getName() + ", doing some damage");
				enemy.takeDmg(this.inventory[3].getStat());
			} else if (this.inventory[3].function.equals("buffAtk")) {
				System.out.println(this.name + "'s attack rises by " + this.inventory[3].getStat());
				this.updateAtk(this.inventory[3].getStat());
			} else if (this.inventory[3].function.equals("buffDef")) {
				System.out.println(this.name + "'s defense rises by " + this.inventory[3].getStat());
				this.updateDef(this.inventory[3].getStat());
			}
		}
	}

	public void attackBoss(Boss boss) {
		this.attackPrompt();
		if (prompt.getPChoice().equalsIgnoreCase("attack")) {
			System.out.println(this.name + " attacks the " + boss.getName());
			double gotHit = 2 * Math.random();
			if (gotHit <= 1) {
				System.out.println("The " + boss.getName() + " narrowly avoided the attack");
			} else {
				System.out.println("the " + boss.getName() + " was hit by the attack");
				boss.takeDmg(this.atk);
			}
		} else if (prompt.getPChoice().equalsIgnoreCase("run")) {
			double gotHit = 6 * Math.random();
			if (gotHit <= 5) {
				System.out.println(this.name + " failed to get away");
			} else {
				System.out.println(this.name + " successfully ran away");
				return;
			}
		} else if (prompt.getPChoice().equalsIgnoreCase(this.inventory[2].getName())) {
			System.out.println(this.name + " uses the " + this.inventory[2].getName());
			if (this.inventory[2].function.equals("Healing")) {
				System.out.println(this.name + " heals " + this.inventory[2].getStat() + " health points");
				this.heal(this.inventory[2].getStat());
			} else if (this.inventory[2].function.equals("Damage")) {
				System.out.println(this.name + " throws the " + this.inventory[2].getName() + " at the " + boss.getName() + ", doing some damage");
				boss.takeDmg(this.inventory[2].getStat());
			} else if (this.inventory[2].function.equals("buffAtk")) {
				System.out.println(this.name + "'s attack rises by " + this.inventory[2].getStat());
				this.updateAtk(this.inventory[2].getStat());
			} else if (this.inventory[2].function.equals("buffDef")) {
				System.out.println(this.name + "'s defense rises by " + this.inventory[2].getStat());
				this.updateDef(this.inventory[2].getStat());
			}
		} else {
			System.out.println("You use the " + this.inventory[3].getName());
			if (this.inventory[3].function.equals("Healing")) {
				System.out.println(this.name + " heals " + this.inventory[3].getStat() + " health points");
				this.heal(this.inventory[3].getStat());
			} else if (this.inventory[3].function.equals("Damage")) {
				System.out.println(this.name + " throws the " + this.inventory[3].getName() + " at the " + boss.getName() + ", doing some damage");
				boss.takeDmg(this.inventory[3].getStat());
			} else if (this.inventory[3].function.equals("buffAtk")) {
				System.out.println(this.name + "'s attack rises by " + this.inventory[3].getStat());
				this.updateAtk(this.inventory[3].getStat());
			} else if (this.inventory[3].function.equals("buffDef")) {
				System.out.println(this.name + "'s defense rises by " + this.inventory[3].getStat());
				this.updateDef(this.inventory[3].getStat());
			}
		}
	}

	public void attackGroup(Enemy[] enemyList, Boolean[] whichGroupAlive, int[] aliveCount, int battle) {
		this.attackPrompt();
		for (int d = 0; d < battle; d++) {
			if (whichGroupAlive[d]) {
				this.whichAttacked = d;
			}
		}
		if (prompt.getPChoice().equalsIgnoreCase("attack")) {
			System.out.println(this.name + " attacks the group of enemies");
			double gotHit = 2 * Math.random();
			if (gotHit <= 1) {
				System.out.println("The " + enemyList[this.whichAttacked].getName() + " narrowly avoided the attack");
			} else {
				System.out.println("the " + enemyList[this.whichAttacked].getName() + " was hit by the attack");
				enemyList[this.whichAttacked].takeDmg(this.atk);
			}
		} else if (prompt.getPChoice().equalsIgnoreCase("run")) {
			double gotHit = 6 * Math.random();
			if (gotHit <= 5) {
				System.out.println(this.name + " failed to get away");
			} else {
				System.out.println(this.name + " succesfully ran away");
				return;
			}
		} else if (prompt.getPChoice().equalsIgnoreCase(this.inventory[2].getName())) {
			System.out.println(this.name + " uses the " + this.inventory[2].getName());
			if (this.inventory[2].function.equals("Healing")) {
				System.out.println(this.name + " heals " + this.inventory[2].getStat() + " health points");
				this.heal(this.inventory[2].getStat());
			} else if (this.inventory[2].function.equals("Damage")) {
				System.out.println(this.name + " throws the " + this.inventory[2].getName() + " at the group of " + enemyList[this.whichAttacked].getName()
						+ ", doing some damage to all of them");
				enemyList[whichAttacked].itemDmg(this.inventory[2].getStat());
			} else if (this.inventory[2].function.equals("buffAtk")) {
				System.out.println(this.name + "'s attack rises by " + this.inventory[2].getStat());
				this.updateAtk(this.inventory[2].getStat());
			} else if (this.inventory[2].function.equals("buffDef")) {
				System.out.println(this.name + "'s defense rises by " + this.inventory[2].getStat());
				this.updateDef(this.inventory[2].getStat());
			}
		} else {
			System.out.println("You use the " + this.inventory[3].getName());
			if (this.inventory[3].function.equals("Healing")) {
				System.out.println(this.name + " heals " + this.inventory[3].getStat() + " health points");
				this.heal(this.inventory[3].getStat());
			} else if (this.inventory[3].function.equals("Damage")) {
				System.out.println(this.name + " throws the " + this.inventory[3].getName() + " at the group of " + enemyList[whichAttacked].getName()
						+ ", doing some damage to all of them");
				enemyList[whichAttacked].itemDmg(this.inventory[3].getStat());
			} else if (this.inventory[3].function.equals("buffAtk")) {
				System.out.println(this.name + "'s attack rises by " + this.inventory[3].getStat());
				this.updateAtk(this.inventory[3].getStat());
			} else if (this.inventory[3].function.equals("buffDef")) {
				System.out.println(this.name + "'s defense rises by " + this.inventory[3].getStat());
				this.updateDef(this.inventory[3].getStat());
			}
		}
	}
}