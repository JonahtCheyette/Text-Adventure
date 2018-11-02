package hi;

import java.util.Scanner;

public class Forest {
	Boolean spiderKilled = false;
	Boolean trollKilled = false;
	Scanner input = new Scanner(System.in);
	Character player;
	Boss[] bossList;
	Enemy[][] enemyList;
	String[] choices = new String[3];
	Battle battle;
	Prompt prompt;

	Forest(Character player, Boss[] bossList, Enemy[][] enemyList, Battle battle) {
		this.player = player;
		this.battle = battle;
		this.bossList = bossList;
		this.enemyList = enemyList;
		this.prompt = new Prompt(player);
	}

	public Boolean forestComplete() {
		if (trollKilled && spiderKilled) {
			return true;
		} else {
			return false;
		}
	}

	public void runForest() {
		player.updateAtk(0);
		player.updateDef(0);
		for (int iiiii = 0; iiiii < this.bossList.length; iiiii++) {
			this.bossList[iiiii].reset();
		}
		for (int io = 0; io < this.enemyList.length; io++) {
			for (int ii = 0; ii < this.enemyList[io].length; ii++) {
				this.enemyList[io][ii].reset();
			}
		}
		System.out.println(player.getName()
				+ " goes into the forest and come across a fork in the road\nOne path runs along a river till it gets to a bridge, and the other leads into a shady part of the forest.");
		choices[0] = "bridge";
		choices[1] = "deep";
		prompt.usePrompt(this.choices);
		if (prompt.getPChoice().equals("bridge")) {
			this.battle.encounters(this.player, this.enemyList[0], "forest");
			if (player.getHealth() <= 0 || battle.prompt.getPChoice().equalsIgnoreCase("run") || battle.prompt.getPChoice().equalsIgnoreCase("stop")) {
				return;
			}
			System.out.println(player.getName()
					+ " is halfway across the bridge when a troll comes out from behid a tree and says \"You need to pay a tax in order to go over the bridge\"\n"
					+ player.getName() + " can either: pay 90 gold, attack the troll, or go back to the crossroads.");
			choices[0] = "pay";
			choices[1] = "attack";
			choices[2] = "run";
			prompt.usePrompt(this.choices);
			if (prompt.getPChoice().equalsIgnoreCase("pay")) {
				System.out.println(player.getName() + " pays the troll 90 gold and he lets you cross");
				if (player.gold < 90) {
					System.out.println(player.getName() + " does not have enough money and the troll attacks them for wasting his time");
					this.battle.bossBattle(player, this.bossList[0]);
				} else {
					System.out.println(player.getName() + " pays the troll and he lets you pass");
					player.updateGold(-90);
				}
			} else if (prompt.getPChoice().equalsIgnoreCase("attack")) {
				System.out.println(player.getName() + " decides to attack the troll");
				this.battle.bossBattle(player, this.bossList[0]);
				if (player.health <= 0) {
					return;
				} else {
					trollKilled = true;
				}
			} else {
				System.out.println(player.getName() + " runs out of the forest in a panic");
				return;
			}
		} else {
			this.battle.encounters(this.player, this.enemyList[1], "forest");
			if (player.getHealth() <= 0 || battle.prompt.getPChoice().equalsIgnoreCase("run") || battle.prompt.getPChoice().equalsIgnoreCase("stop")) {
				return;
			}
			System.out.println(player.getName() + " goes deeper into the woods, and notices that the spiders are becoming more plentiful the deeper they go\n"
					+ player.getName() + "ends up in a clearing, with a giant spider in front of them hanging from a spiderweb.");
			this.prompt.pause();
			battle.bossBattle(player, this.bossList[1]);
			spiderKilled = true;
		}
	}
}