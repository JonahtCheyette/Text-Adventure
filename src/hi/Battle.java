package hi;

import java.util.Random;
import java.util.Scanner;

public class Battle {
	int battle;
	int playerAtkInit;
	int playerDefInit;
	int groupDead;
	int whichToAttack;
	int[] aliveCount = new int[5];
	int[][] effectLast = new int[][] { { -1, -1 }, { -1, -1 } };
	int[][] tempStat = new int[][] { { 0, 0 }, { 0, 0 } };
	String[] item = new String[2];
	String[] choices = new String[] { "keep going", "stop" };
	Boolean itemUsed = false;
	Boolean battleGoing = false;
	Boolean[] whichGroupAlive = new Boolean[5];
	Scanner input = new Scanner(System.in);
	Random rand = new Random();
	Prompt prompt;

	Battle() {

	}

	public void checkItemExpired(Character player) {
		player.setDef(this.playerDefInit);
		if (this.effectLast[1][0] > 0) {
			player.updateDef(this.tempStat[1][0]);
		}
		if (this.effectLast[1][1] > 0) {
			player.updateDef(this.tempStat[1][1]);
		}
		player.setAtk(this.playerAtkInit);
		if (this.effectLast[0][0] > 0) {
			player.updateDef(this.tempStat[0][0]);
		}
		if (this.effectLast[0][1] > 0) {
			player.updateDef(this.tempStat[0][1]);
		}
		if (this.effectLast[0][0] == 0 || this.effectLast[1][0] == 0) {
			System.out.println(player.getName() + "'s " + this.item[0] + " wore off");
		}
		if (this.effectLast[0][1] == 0 || this.effectLast[1][1] == 0) {
			System.out.println(player.getName() + "'s " + this.item[1] + " wore off");
		}
		for (int i = 0; i < this.effectLast.length; i++) {
			for (int j = 0; j < this.tempStat[i].length; j++) {
				this.effectLast[i][j] -= 1;
			}
		}
	}
	
	public void checkItemUsed(Character player) {
		if (player.getItem1() != null) {
			if (player.prompt.getPChoice().equalsIgnoreCase(player.getItem1().getName())) {
				if (player.getItem1().function.equals("buffAtk")) {
					if (!this.itemUsed) {
						this.item[0] = player.getItem1().getName();
						this.tempStat[0][0] = player.getItem1().getStat();
						this.effectLast[0][0] = player.getItem1().getLast();
					} else {
						this.item[1] = player.getItem1().getName();
						this.tempStat[0][1] = player.getItem1().getStat();
						this.effectLast[0][1] = player.getItem1().getLast();
					}
					this.itemUsed = true;
				} else if (player.getItem1().function.equals("buffDef")) {
					if (!this.itemUsed) {
						this.item[0] = player.getItem1().getName();
						this.tempStat[1][0] = player.getItem1().getStat();
						this.effectLast[1][0] = player.getItem1().getLast();
					} else {
						this.item[1] = player.getItem1().getName();
						this.tempStat[1][1] = player.getItem1().getStat();
						this.effectLast[1][1] = player.getItem1().getLast();
					}
					this.itemUsed = true;
				}
				player.useItem(2);
			}
		}
		if (player.getItem2() != null) {
			if (player.prompt.getPChoice().equalsIgnoreCase(player.getItem2().getName())) {
				this.item[0] = player.getItem2().getName();
				this.itemUsed = true;
				if (player.getItem2().function.equals("buffAtk")) {
					this.tempStat[0][0] = player.getItem2().getStat();
					this.effectLast[0][0] = player.getItem2().getLast();
				} else if (player.getItem2().function.equals("buffDef")) {
					this.tempStat[1][0] = player.getItem2().getStat();
					this.effectLast[1][0] = player.getItem2().getLast();
				}
				player.useItem(3);
			}
		}
	}

	public void encounters(Character player, Mob[] enemyList, String place) {
		this.prompt = new Prompt(player);
		this.battle = 1;
		this.groupDead = 0;
		this.battleGoing = true;
		for (int k = 0; k < effectLast.length; k++) {
			for (int l = 0; l < tempStat[l].length; l++) {
				effectLast[k][l] = -1;
				tempStat[k][l] = 0;
			}
		}
		this.playerAtkInit = player.getAtk();
		this.playerDefInit = player.getDef();
		for (int m = 0; m < enemyList.length; m++) {
			if (m > 0) {
				System.out.println(player.getName() + " goes farther into the " + place + " and gets attacked by some enemies");
			} else {
				System.out.println(player.getName() + " goes into the " + place + " and gets attacked by some enemies");
			}
			for (int n = 0; n < enemyList.length; n++) {
				enemyList[n].reset();
			}
			while (this.battleGoing) {
				this.checkItemExpired(player);
				this.groupDead = 0;
				for (int o = 0; o < this.battle; o++) {
					this.aliveCount[o] = enemyList[o].getCount();
					for (int p = 0; p < enemyList[o].getCount(); p++) {
						if (enemyList[o].getHealth(p) <= 0) {
							this.aliveCount[o]--;
						}
					}
					if (this.aliveCount[o] <= 0) {
						this.whichGroupAlive[o] = false;
					} else {
						this.whichGroupAlive[o] = true;
						this.whichToAttack = o;
						
					}
				}
				for (int q = 0; q < this.battle; q++) {
					if (!this.whichGroupAlive[q]) {
						this.groupDead++;
					}
				}
				if (this.groupDead < this.battle && player.getHealth() > 0) {
					System.out.println(player.getName() + " is fighting against:");
					for (int r = 0; r < this.battle; r++) {
						if (this.aliveCount[r] > 0) {
							if (this.aliveCount[r] == 1) {
								System.out.println(this.aliveCount[r] + " " + enemyList[r].getName());
							} else {
								System.out.println(this.aliveCount[r] + " " + enemyList[r].getName() + "s");
							}
						}
					}
					System.out.println("The enemies attack!");
					for (int s = 0; s < this.battle; s++) {
						for (int ih = 0; ih < this.aliveCount[s]; ih++) {
							enemyList[s].attack(ih, player);
						}
						if (this.aliveCount[s] > 0) {
							if (this.aliveCount[s] == 1) {
								if (enemyList[s].minionHealth > 0) {
									if (enemyList[s].attacked == 1) {
										System.out.println("The " + enemyList[s].getName() + " landed an attack");
									} else if (enemyList[s].whiffed == 1) {
										System.out.println("The " + enemyList[s].getName() + " tried to attack but missed");
									} else {
										System.out.println("The " + enemyList[s].getName() + " summoned a " + enemyList[s].getMinionName());
									}
								} else {
									if (enemyList[s].attacked == 1) {
										System.out.println("The " + enemyList[s].getName() + " landed an attack");
									} else {
										System.out.println("The " + enemyList[s].getName() + " tried to attack but missed");
									}
								}
							} else {
								if (enemyList[s].minionHealth > 0) {
									System.out.println("Of the " + this.aliveCount[s] + " " + enemyList[s].getName() + "s,\n" + enemyList[s].attacked
											+ " landed an attack, " + enemyList[s].summoned + " summoned " + enemyList[s].getMinionName() + "s, and "
											+ enemyList[s].whiffed + " tried to attack but missed");
								} else {
									System.out.println("Of the " + this.aliveCount[s] + " " + enemyList[s].getName() + "s,\n" + enemyList[s].attacked
											+ " landed an attack, and " + enemyList[s].whiffed + " tried to attack but missed");
								}
							}
						}
						enemyList[s].resetCounter();
					}
					player.attack(enemyList[this.whichToAttack]);
					if (player.prompt.getPChoice().equalsIgnoreCase("run")) {
						this.prompt.setChoice("run");
						return;
					}
					this.checkItemUsed(player);
				}
				if (player.getHealth() <= 0) {
					player.setDef(this.playerDefInit);
					player.setAtk(this.playerAtkInit);
					return;
				}
				if (this.groupDead >= this.battle) {
					this.battleGoing = false;
				} else {
					this.groupDead = 0;
				}
			}
			player.setDef(this.playerDefInit);
			player.setAtk(this.playerAtkInit);
			if (!(player.getHealth() <= 0)) {
				for (int t = 0; t < this.battle; t++) {
					player.updateGold(enemyList[t].giveGold());
				}
				this.itemUsed = false;
				System.out.println(player.getName() + " won and gained gold! " + player.getName() + " now has " + player.getGold() + " pieces of gold. "
						+ player.getName() + " now has " + player.getHealth() + " health");
			}
			this.battle++;
			System.out.println(player.getName() + " can either keep going or stop.");
			this.prompt.usePrompt(this.choices);
			if (this.prompt.getPChoice().equalsIgnoreCase("stop")) {
				return;
			} else {
				this.battleGoing = true;
			}
		}
	}

	public void bossBattle(Character player, Boss enemy) {
		this.prompt = new Prompt(player);
		for (int u = 0; u < effectLast.length; u++) {
			for (int v = 0; v < tempStat[u].length; v++) {
				effectLast[u][v] = -1;
				tempStat[u][v] = 0;
			}
		}
		this.playerAtkInit = player.getAtk();
		this.playerDefInit = player.getDef();
		while (enemy.getHealth() > 0 && player.getHealth() > 0) {
			this.checkItemExpired(player);
			if (enemy.getName().equals("Eclipse")) {
				System.out.println("Eclipse attacks!");
			} else {
				System.out.println("The " + enemy.getName() + " attacks!");
			}
			enemy.attack(player);
			if (player.getHealth() > 0) {
				player.attack(enemy);
				this.checkItemUsed(player);
			}
			if (player.getHealth() <= 0) {
				player.setDef(this.playerDefInit);
				player.setAtk(this.playerAtkInit);
				return;
			}
		}
		player.setDef(this.playerDefInit);
		player.setAtk(this.playerAtkInit);
		if (!(player.getHealth() <= 0)) {
			player.updateGold(enemy.giveGold());
			this.itemUsed = false;
			System.out.println("You won and gained gold! you now have " + player.getGold() + " pieces of gold. You now have " + player.getHealth() + " health");
		}
	}
}