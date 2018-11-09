package hi;

import java.util.Random;

public class Boss {
	int health;
	int startHealth;
	int atk;
	int startAtk;
	int goldMax;
	int minionHealth;
	int damageTook;
	int expMax;
	int expMin;
	int minions = 0;
	int cMinions = 0;
	int[] pMHealth = new int[15];
	String name;
	String mName;
	Random rand = new Random();

	Boss(String name, String mName, int health, int atk, int minionHealth, int goldMax, int expMax, int expMin) {
		this.health = health;
		this.startHealth = health;
		this.atk = atk;
		this.startAtk = atk;
		this.goldMax = goldMax;
		this.minionHealth = minionHealth;
		this.expMax = expMax;
		this.expMin = expMin;
		this.name = name;
		this.mName = mName;
		for (int w = 0; w < this.pMHealth.length; w++) {
			this.pMHealth[w] = 0;
		}
	}
	
	public int getHealth() {
		return this.health;
	}

	public int getAtk() {
		return this.atk;
	}

	public int getGoldMax() {
		return this.goldMax;
	}

	public int getMinionHealth() {
		return this.minionHealth;
	}

	public String getName() {
		return this.name;
	}

	public String getMinionName() {
		return this.mName;
	}

	public int calculateExp() {
		return rand.nextInt(this.expMax + 1 - this.expMin) + this.expMin;
	}
	
	public void takeDmg(int Dmg) {
		this.cMinions = this.minions;
		this.damageTook = Dmg;
		if (this.minions > 0) {
			for (int x = 0; x < this.minions; x++) {
				if (this.damageTook > this.pMHealth[x]) {
					this.cMinions--;
					this.damageTook -= this.pMHealth[x];
				} else {
					this.pMHealth[x] -= this.damageTook;
				}
				if (this.damageTook < 0) {
					this.damageTook = 0;
				}
			}
			for (int y = this.cMinions + 1; y < this.minions + 1; y++) {
				this.pMHealth[y] = 0;
			}
			this.health -= this.damageTook;
			this.minions = this.cMinions;
		} else {
			this.health -= Dmg;
		}
		if (this.minions < 0) {
			this.minions = 0;
		}
		if (this.health < 0) {
			this.health = 0;
		}
	}

	public int giveGold() {
		return (int) (this.goldMax * Math.random());
	}

	public void reset() {
		this.atk = this.startAtk;
		this.health = this.startHealth;
		this.minions = 0;
	}

	public void attack(Character player) {
		if (this.minionHealth > 0) {
			int gotHit = rand.nextInt(30);
			if (gotHit < 10) {
				System.out.println(player.getName() + " narrowly avoided the " + this.name + "'s attack");
			} else if (gotHit >= 10 && gotHit < 13) {
				System.out.println("The " + this.name + " summoned a " + this.mName);
				this.summonMinions();
			} else {
				if (this.getName().equals("Eclipse")) {
					System.out.println("you cannot grasp the true nature of Eclipse's attack");
				} else {
					System.out.println(player.getName() + " was hit by the " + this.name + "'s attack");
				}
				player.takeDmg(this.atk);
			}
		} else {
			int gotHit = rand.nextInt(2);
			if (gotHit == 0) {
				System.out.println(player.getName() + " narrowly avoided the " + this.name + "'s attack");
			} else {
				System.out.println(player.getName() + " was hit by the " + this.name + "'s attack");
				player.takeDmg(this.atk);
			}
		}
	}

	public void summonMinions() {
		this.minions++;
		this.pMHealth[this.minions - 1] = this.minionHealth;
		this.atk = (this.startAtk + (5 * this.minions));
	}
}