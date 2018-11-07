package hi;

import java.util.Random;
import java.util.Scanner;

public class Battle {
	static int battle;
	static int playerAtkInit;
	static int playerDefInit;
	static int groupDead;
	static int whichToAttack;
	static int[] aliveCount = new int[5];
	static int[][] effectLast = new int[][] { { -1, -1 }, { -1, -1 } };
	static int[][] tempStat = new int[][] { { 0, 0 }, { 0, 0 } };
	static String[] item = new String[2];
	static String[] choices = new String[] { "keep going", "stop" };
	static Boolean itemUsed = false;
	static Boolean battleGoing = false;
	static Boolean[] whichGroupAlive = new Boolean[5];
	static Scanner input = new Scanner(System.in);
	static Random rand = new Random();
	static Character player = GameWorld.getPlayer();

	Battle(){
		
	}
	
	public static void checkItemExpired() {
		player.setDef(playerDefInit);
		if (effectLast[1][0] > 0) {
			player.updateDef(tempStat[1][0]);
		}
		if (effectLast[1][1] > 0) {
			player.updateDef(tempStat[1][1]);
		}
		player.setAtk(playerAtkInit);
		if (effectLast[0][0] > 0) {
			player.updateDef(tempStat[0][0]);
		}
		if (effectLast[0][1] > 0) {
			player.updateDef(tempStat[0][1]);
		}
		if (effectLast[0][0] == 0 || effectLast[1][0] == 0) {
			System.out.println(player.getName() + "'s " + item[0] + " wore off");
		}
		if (effectLast[0][1] == 0 || effectLast[1][1] == 0) {
			System.out.println(player.getName() + "'s " + item[1] + " wore off");
		}
		for (int i = 0; i < effectLast.length; i++) {
			for (int j = 0; j < tempStat[i].length; j++) {
				effectLast[i][j] -= 1;
			}
		}
	}
	
	public static void checkItemUsed() {
		if (player.getItem1() != null) {
			if (Prompt.getPChoice().equalsIgnoreCase(player.getItem1().getName())) {
				if (player.getItem1().function.equals("buffAtk")) {
					if (!itemUsed) {
						item[0] = player.getItem1().getName();
						tempStat[0][0] = player.getItem1().getStat();
						effectLast[0][0] = player.getItem1().getLast();
					} else {
						item[1] = player.getItem1().getName();
						tempStat[0][1] = player.getItem1().getStat();
						effectLast[0][1] = player.getItem1().getLast();
					}
					itemUsed = true;
				} else if (player.getItem1().function.equals("buffDef")) {
					if (!itemUsed) {
						item[0] = player.getItem1().getName();
						tempStat[1][0] = player.getItem1().getStat();
						effectLast[1][0] = player.getItem1().getLast();
					} else {
						item[1] = player.getItem1().getName();
						tempStat[1][1] = player.getItem1().getStat();
						effectLast[1][1] = player.getItem1().getLast();
					}
					itemUsed = true;
				}
				player.useItem(2);
			}
		}
		if (player.getItem2() != null) {
			if (Prompt.getPChoice().equalsIgnoreCase(player.getItem2().getName())) {
				item[0] = player.getItem2().getName();
				itemUsed = true;
				if (player.getItem2().function.equals("buffAtk")) {
					tempStat[0][0] = player.getItem2().getStat();
					effectLast[0][0] = player.getItem2().getLast();
				} else if (player.getItem2().function.equals("buffDef")) {
					tempStat[1][0] = player.getItem2().getStat();
					effectLast[1][0] = player.getItem2().getLast();
				}
				player.useItem(3);
			}
		}
	}

	public static void encounters(Mob[] enemyList, String place) {
		battle = 1;
		groupDead = 0;
		battleGoing = true;
		for (int k = 0; k < effectLast.length; k++) {
			for (int l = 0; l < tempStat[k].length; l++) {
				effectLast[k][l] = -1;
				tempStat[k][l] = 0;
			}
		}
		playerAtkInit = player.getAtk();
		playerDefInit = player.getDef();
		for (int m = 0; m < enemyList.length; m++) {
			if (m > 0) {
				System.out.println(player.getName() + " goes farther into the " + place + " and gets attacked by some enemies");
			} else {
				System.out.println(player.getName() + " goes into the " + place + " and gets attacked by some enemies");
			}
			for (int n = 0; n < enemyList.length; n++) {
				enemyList[n].reset();
			}
			while (battleGoing) {
				Battle.checkItemExpired();
				groupDead = 0;
				for (int o = 0; o < battle; o++) {
					aliveCount[o] = enemyList[o].getCount();
					for (int p = 0; p < enemyList[o].getCount(); p++) {
						if (enemyList[o].getHealth(p) <= 0) {
							aliveCount[o]--;
						}
					}
					if (aliveCount[o] <= 0) {
						whichGroupAlive[o] = false;
					} else {
						whichGroupAlive[o] = true;
						whichToAttack = o;
						
					}
				}
				for (int q = 0; q < battle; q++) {
					if (!whichGroupAlive[q]) {
						groupDead++;
					}
				}
				if (groupDead < battle && player.getHealth() > 0) {
					System.out.println(player.getName() + " is fighting against:");
					for (int r = 0; r < battle; r++) {
						if (aliveCount[r] > 0) {
							if (aliveCount[r] == 1) {
								System.out.println(aliveCount[r] + " " + enemyList[r].getName());
							} else {
								System.out.println(aliveCount[r] + " " + enemyList[r].getName() + "s");
							}
						}
					}
					System.out.println("The enemies attack!");
					for (int s = 0; s < battle; s++) {
						for (int ih = 0; ih < aliveCount[s]; ih++) {
							enemyList[s].attack(ih, player);
						}
						if (aliveCount[s] > 0) {
							if (aliveCount[s] == 1) {
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
									System.out.println("Of the " + aliveCount[s] + " " + enemyList[s].getName() + "s,\n" + enemyList[s].attacked
											+ " landed an attack, " + enemyList[s].summoned + " summoned " + enemyList[s].getMinionName() + "s, and "
											+ enemyList[s].whiffed + " tried to attack but missed");
								} else {
									System.out.println("Of the " + aliveCount[s] + " " + enemyList[s].getName() + "s,\n" + enemyList[s].attacked
											+ " landed an attack, and " + enemyList[s].whiffed + " tried to attack but missed");
								}
							}
						}
						enemyList[s].resetCounter();
					}
					player.attack(enemyList[whichToAttack]);
					if (Prompt.checkPChoice(false,"run")) {
						Prompt.setChoice("run");
						return;
					}
					Battle.checkItemUsed();
				}
				if (player.getHealth() <= 0) {
					player.setDef(playerDefInit);
					player.setAtk(playerAtkInit);
					return;
				}
				if (groupDead >= battle) {
					battleGoing = false;
				} else {
					groupDead = 0;
				}
			}
			player.setDef(playerDefInit);
			player.setAtk(playerAtkInit);
			if (!(player.getHealth() <= 0)) {
				for (int t = 0; t < battle; t++) {
					player.updateGold(enemyList[t].giveGold());
					player.gainExp(enemyList[t].calculateExp(), battle);
				}
				itemUsed = false;
				System.out.println(player.getName() + " won and gained gold! " + player.getName() + " now has " + player.getGold() + " pieces of gold. "
						+ player.getName() + " now has " + player.getHealth() + " health");
			}
			battle++;
			System.out.println(player.getName() + " can either keep going or stop.");
			Prompt.usePrompt(player,choices);
			if (Prompt.checkPChoice(false,"stop")) {
				return;
			} else {
				battleGoing = true;
			}
		}
	}

	public static void bossBattle(Boss enemy) {
		for (int u = 0; u < effectLast.length; u++) {
			for (int v = 0; v < tempStat[u].length; v++) {
				effectLast[u][v] = -1;
				tempStat[u][v] = 0;
			}
		}
		playerAtkInit = player.getAtk();
		playerDefInit = player.getDef();
		while (enemy.getHealth() > 0 && player.getHealth() > 0) {
			Battle.checkItemExpired();
			if (enemy.getName().equals("Eclipse")) {
				System.out.println("Eclipse attacks!");
			} else {
				System.out.println("The " + enemy.getName() + " attacks!");
			}
			enemy.attack(player);
			if (player.getHealth() > 0) {
				player.attack(enemy);
				Battle.checkItemUsed();
			}
			if (player.getHealth() <= 0) {
				player.setDef(playerDefInit);
				player.setAtk(playerAtkInit);
				return;
			}
			if(Prompt.checkPChoice(false,"run") && player.getCheckRan()) {
				return;
			}
		}
		player.setDef(playerDefInit);
		player.setAtk(playerAtkInit);
		if (!(player.getHealth() <= 0)) {
			player.updateGold(enemy.giveGold());
			player.gainExp(enemy.calculateExp(), 1);
			itemUsed = false;
			System.out.println("You won and gained gold! you now have " + player.getGold() + " pieces of gold. You now have " + player.getHealth() + " health");
		}
	}
}