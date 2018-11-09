package hi;

public class Mob extends Boss{
	int whichDmg;
	int rangeMax;
	int rangeMin;
	int expMax;
	int expMin;
	int attacked = 0;
	int summoned = 0;
	int whiffed = 0;
	int count = 0;
	int[] atk;
	int[] health;
	int[] minions;
	Boolean damageTookCheck = false;

	Mob(String name, String mName, int health, int atk, int rangeMin, int rangeMax, int minionHealth, int goldMax, int expMax, int expMin) {
		super(name, mName, health, atk, goldMax, minionHealth, expMax, expMin);
		this.rangeMin = rangeMin;
		this.rangeMax = rangeMax;
		this.atk = new int[rangeMax];
		this.health = new int[rangeMax];
		this.minions = new int[rangeMax];
		for (int ic = rangeMin; ic < rangeMax; ic++) {
			this.atk[ic] = -1;
			this.health[ic] = -1;
			if (minionHealth > 0) {
				this.minions[ic] = -1;
			}
		}
		for (int id = 0; id < this.pMHealth.length; id++) {
			this.pMHealth[id] = 0;
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

	public void takeDmg(int Dmg) {
		this.damageTook = Dmg;
		for (int ie = 0; ie < this.health.length; ie++) {
			if (!this.damageTookCheck && this.health[ie] > 0) {
				this.whichDmg = ie;
				this.damageTookCheck = true;
			}
		}
		this.cMinions = this.minions[whichDmg];
		if (this.minions[this.whichDmg] > 0) {
			System.out.println(this.cMinions);
			for (int ig = 0; ig < this.minions[this.whichDmg]; ig++) {
				if (this.damageTook > this.pMHealth[ig]) {
					this.cMinions--;
					this.damageTook -= this.pMHealth[ig];
				} else {
					this.pMHealth[ig] -= this.damageTook;
				}
				if (this.damageTook < 0) {
					this.damageTook = 0;
				}
			}
			for (int ih = this.cMinions + 1; ih < this.minions[this.whichDmg] + 1; ih++) {
				this.pMHealth[ih] = 0;
			}
			this.health[this.whichDmg] -= this.damageTook;
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
		return super.giveGold() * this.count;
	}

	public int calculateExp() {
		return super.calculateExp() * this.count;
	}
	
	public void reset() {
		this.count = (rand.nextInt((this.rangeMax - this.rangeMin) + 1) + this.rangeMin);
		for (int ii = this.rangeMin; ii < this.rangeMax; ii++) {
			this.atk[ii] = -1;
			this.health[ii] = -1;
			if (minionHealth > 0) {
				this.minions[ii] = -1;
			}
		}
		for (int ij = 0; ij < this.count; ij++) {
			this.atk[ij] = this.startAtk;
			this.health[ij] = this.startHealth;
			if (minionHealth > 0) {
				this.minions[ij] = 0;
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
		this.minions[which] ++;
		this.atk[which] = (this.startAtk + 5 * this.minions[which]);
		this.pMHealth[this.minions[which] - 1] = this.minionHealth;
	}
}