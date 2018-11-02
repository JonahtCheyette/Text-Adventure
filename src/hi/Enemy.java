package hi;

import java.util.Random;

public class Enemy {
	int whichDmg;
	int damageTook;
	int rangeMax;
	int rangeMin;
	int startHealth;
	int startAtk;
	int goldMax;
	int minionHealth;
	int attacked = 0;
	int summoned = 0;
	int whiffed = 0;
	int cMinions = 0;
	int count = 0;
	int[] atk;
	int[] health;
	int[] minions;
	int[] pMHealth = new int[15];
	String name;
	String mName;
	Boolean damageTookCheck = false;
	Random rand = new Random();

	Enemy(String name, String mName, int health, int atk, int rangeMin, int rangeMax, int minionHealth, int goldMax) {
		this.goldMax = goldMax;
		this.rangeMin = rangeMin;
		this.rangeMax = rangeMax;
		this.minionHealth = minionHealth;
		this.name = name;
		this.mName = mName;
		this.startHealth = health;
		this.startAtk = atk;
		this.atk = new int[rangeMax];
		this.health = new int[rangeMax];
		this.minions = new int[rangeMax];
		for (int o = rangeMin; o < rangeMax; o++) {
			this.atk[o] = -1;
			this.health[o] = -1;
			if (minionHealth > 0) {
				this.minions[o] = -1;
			}
		}
		for (int i = 0; i < this.pMHealth.length; i++) {
			this.pMHealth[i] = 0;
		}
	}

	public int getHealth(int which) {
		return this.health[which];
	}

	public int getMinions(int which) {
		return this.minions[which];
	}

	public int getCount() {
		return this.count;
	}

	public String getName() {
		return this.name;
	}

	public String getMName() {
		return this.mName;
	}

	public void takeDmg(int Dmg) {
		for (int p = 0; p < this.health.length; p++) {
			if (!this.damageTookCheck && this.health[p] > 0) {
				this.whichDmg = p;
				this.damageTookCheck = true;
			}
		}
		this.cMinions = this.minions[whichDmg];
		if (this.minions[this.whichDmg] > 0) {
			for (int i = 0; i < this.minions[this.whichDmg]; i++) {
				if (this.damageTook > this.pMHealth[i]) {
					this.cMinions--;
					this.damageTook -= this.pMHealth[i];
				} else {
					this.pMHealth[i] -= this.damageTook;
				}
				if (this.damageTook < 0) {
					this.damageTook = 0;
				}
			}
			for (int i = this.cMinions + 1; i < this.minions[this.whichDmg] + 1; i++) {
				this.pMHealth[i] = 0;
			}
			this.health[whichDmg] -= this.damageTook;
			this.minions[this.whichDmg] = this.cMinions;
		} else {
			this.health[this.whichDmg] -= Dmg;
		}
		if (this.minions[this.whichDmg] < 0) {
			this.minions[this.whichDmg] = -1;
		}
		if (this.health[this.whichDmg] < 0) {
			this.health[this.whichDmg] = 0;
		}
		this.damageTookCheck = false;
	}

	public void itemDmg(int Dmg) {
		for (int q = 0; q < this.health.length; q++) {
			this.health[q] -= Dmg;
			if (this.health[this.whichDmg] < 0) {
				this.health[this.whichDmg] = 0;
			}
		}
	}

	public int giveGold() {
		return (int) (this.count * this.goldMax * Math.random());
	}

	public void reset() {
		this.count = (rand.nextInt((this.rangeMax - this.rangeMin) + 1) + this.rangeMin);
		for (int r = this.rangeMin; r < this.rangeMax; r++) {
			this.atk[r] = -1;
			this.health[r] = -1;
			if (minionHealth > 0) {
				this.minions[r] = -1;
			}
		}
		for (int s = 0; s < this.count; s++) {
			this.atk[s] = this.startAtk;
			this.health[s] = this.startHealth;
			if (minionHealth > 0) {
				this.minions[s] = 0;
			}
		}
	}

	public void attack(int which, Character player) {
		if (this.minionHealth > 0) {
			int gotHit = rand.nextInt(30);
			if (gotHit < 10) {
				this.whiffed++;
			} else if (gotHit >= 10 && gotHit < 13) {
				this.summonMinions(which);
				this.summoned++;
			} else {
				player.takeDmg(this.atk[which]);
				this.attacked++;
			}
		} else {
			int gotHit = rand.nextInt(2);
			if (gotHit == 0) {
				this.whiffed++;
			} else {
				player.takeDmg(this.atk[which]);
				this.attacked++;
			}
		}
	}

	public void resetCounter() {
		this.summoned = 0;
		this.attacked = 0;
		this.whiffed = 0;
	}

	public void summonMinions(int which) {
		this.minions[which] += 1;
		this.atk[which] = (this.startAtk + 5 * this.minions[which]);
	}
}