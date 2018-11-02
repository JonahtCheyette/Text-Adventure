package hi;

import java.util.Random;
import java.util.Scanner;

public class Battle {
	int battle;
	int playerAtkInit;
	int playerDefInit;
	int groupDead;
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

	public void encounters(Character player, Enemy[] enemyList, String place) {
		this.prompt = new Prompt(player);
		this.battle = 1;
		this.groupDead = 0;
		this.battleGoing = true;
		for (int y = 0; y < effectLast.length; y++) {
			for (int x = 0; x < tempStat[y].length; x++) {
				effectLast[y][x] = -1;
				tempStat[y][x] = 0;
			}
		}
		this.playerAtkInit = player.getAtk();
		this.playerDefInit = player.getDef();
		for (int v = 0; v < 5; v++) {
			if (v > 0) {
				System.out.println(player.getName() + " goes farther into the " + place + " and gets attacked by some enemies");
			} else {
				System.out.println(player.getName() + " goes into the " + place + " and gets attacked by some enemies");
			}
			for (int f = 0; f < enemyList.length; f++) {
				enemyList[f].reset();
			}
			this.groupDead = 0;
			for (int w = 0; w < this.battle; w++) {
				this.whichGroupAlive[w] = true;
				this.aliveCount[w] = enemyList[w].getCount();
			}
			while (this.battleGoing) {
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
				for (int y = 0; y < effectLast.length; y++) {
					for (int x = 0; x < tempStat[y].length; x++) {
						effectLast[y][x] -= 1;
					}
				}
				this.groupDead = 0;
				for (int x = 0; x < this.battle; x++) {
					this.aliveCount[x] = 0;
					for (int y = 0; y < enemyList[x].getCount(); y++) {
						if (enemyList[x].getHealth(y) > 0) {
							this.aliveCount[x]++;
						}
					}
					if (this.aliveCount[x] <= 0) {
						this.whichGroupAlive[x] = false;
					}
				}
				for (int z = 0; z < this.battle; z++) {
					if (!this.whichGroupAlive[z]) {
						this.groupDead++;
					}
				}
				if (this.groupDead < this.battle && player.getHealth() > 0) {
					System.out.println(player.getName() + " is fighting against:");
					for (int a = 0; a < this.battle; a++) {
						if (this.aliveCount[a] > 0) {
							if (this.aliveCount[a] == 1) {
								System.out.println(this.aliveCount[a] + " " + enemyList[a].getName());
							} else {
								System.out.println(this.aliveCount[a] + " " + enemyList[a].getName() + "s");
							}
						}
					}
					System.out.println("The enemies attack!");
					for (int b = 0; b < this.battle; b++) {
						for (int ih = 0; ih < this.aliveCount[b]; ih++) {
							enemyList[b].attack(ih, player);
						}
						if (this.aliveCount[b] > 0) {
							if (this.aliveCount[b] == 1) {
								if (enemyList[b].minionHealth > 0) {
									if (enemyList[b].attacked == 1) {
										System.out.println("The " + enemyList[b].getName() + " landed an attack");
									} else if (enemyList[b].whiffed == 1) {
										System.out.println("The " + enemyList[b].getName() + " tried to attack but missed");
									} else {
										System.out.println("The " + enemyList[b].getName() + " summoned a " + enemyList[b].getMName());
									}
								} else {
									if (enemyList[b].attacked == 1) {
										System.out.println("The " + enemyList[b].getName() + " landed an attack");
									} else {
										System.out.println("The " + enemyList[b].getName() + " tried to attack but missed");
									}
								}
							} else {
								if (enemyList[b].minionHealth > 0) {
									System.out.println("Of the " + this.aliveCount[b] + " " + enemyList[b].getName() + "s,\n" + enemyList[b].attacked
											+ " landed an attack, " + enemyList[b].summoned + " summoned " + enemyList[b].getMName() + "s, and "
											+ enemyList[b].whiffed + " tried to attack but missed");
								} else {
									System.out.println("Of the " + this.aliveCount[b] + " " + enemyList[b].getName() + "s,\n" + enemyList[b].attacked
											+ " landed an attack, and " + enemyList[b].whiffed + " tried to attack but missed");
								}
							}
						}
						enemyList[b].resetCounter();
					}
					player.attackGroup(enemyList, this.whichGroupAlive, this.aliveCount, this.battle);
					if (player.prompt.getPChoice().equalsIgnoreCase("run")) {
						this.prompt.setChoice("run");
						return;
					}
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
				for (int e = 0; e < this.battle; e++) {
					player.updateGold(enemyList[e].giveGold());
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
		for (int y = 0; y < effectLast.length; y++) {
			for (int x = 0; x < tempStat[y].length; x++) {
				effectLast[y][x] = -1;
				tempStat[y][x] = 0;
			}
		}
		this.playerAtkInit = player.getAtk();
		this.playerDefInit = player.getDef();
		while (enemy.getHealth() > 0 && player.getHealth() > 0) {
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
			for (int y = 0; y < effectLast.length; y++) {
				for (int x = 0; x < tempStat[y].length; x++) {
					effectLast[y][x] -= 1;
				}
			}
			if (enemy.getName().equals("Eclipse")) {
				System.out.println("Eclipse attacks!");
			} else {
				System.out.println("The " + enemy.getName() + " attacks!");
			}
			enemy.attack(player);
			if (player.getHealth() > 0) {
				player.attackBoss(enemy);
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
						} else if (player.getItem1().function.equals("buffDef")) {
							this.tempStat[1][0] = player.getItem2().getStat();
							this.effectLast[1][0] = player.getItem2().getLast();
						}
						player.useItem(3);
					}
				}
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