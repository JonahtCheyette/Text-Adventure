package hi;

import java.util.Scanner;

public class Forest {
	Boolean spiderKilled = false;
	Boolean trollKilled = false;
	Scanner input = new Scanner(System.in);
	Character player = GameWorld.getPlayer();
	Boss[] bossList;
	Mob[][] enemyList;
	String[] choices = new String[3];

	Forest(Boss[] bossList, Mob[][] enemyList) {
		this.bossList = bossList;
		this.enemyList = enemyList;
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
		for (int g = 0; g < this.bossList.length; g++) {
			this.bossList[g].reset();
		}
		for (int h = 0; h < this.enemyList.length; h++) {
			for (int ia = 0; ia < this.enemyList[h].length; ia++) {
				this.enemyList[h][ia].reset();
			}
		}
		System.out.println(player.getName()
				+ " goes into the forest and come across a fork in the road\nOne path runs along a river till it gets to a bridge, and the other leads into a shady part of the forest.");
		choices[0] = "bridge";
		choices[1] = "deep";
		Prompt.usePrompt(this.player,this.choices);
		if (Prompt.checkPChoice(false,"bridge")) {
			Battle.encounters(this.enemyList[0], "forest");
			if (player.getHealth() <= 0 || Prompt.checkPChoice(false,"run", "stop")) {
				return;
			}
			System.out.println(player.getName()
					+ " is halfway across the bridge when a troll comes out from behid a tree and says \"You need to pay a tax in order to go over the bridge\"\n"
					+ player.getName() + " can either: pay 90 gold, attack the troll, or go back to the crossroads.");
			choices[0] = "pay";
			choices[1] = "attack";
			choices[2] = "run";
			Prompt.usePrompt(this.player,this.choices);
			if (Prompt.checkPChoice(false,"pay")) {
				System.out.println(player.getName() + " pays the troll 90 gold and he lets you cross");
				if (player.gold < 90) {
					System.out.println(player.getName() + " does not have enough money and the troll attacks them for wasting his time");
					Battle.bossBattle(this.bossList[0]);
				} else {
					System.out.println(player.getName() + " pays the troll and he lets you pass");
					player.updateGold(-90);
				}
			} else if (Prompt.checkPChoice(false,"attack")) {
				System.out.println(player.getName() + " decides to attack the troll");
				Battle.bossBattle(this.bossList[0]);
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
			Battle.encounters(this.enemyList[1], "forest");
			if (player.getHealth() <= 0 || Prompt.checkPChoice(false,"run", "stop")) {
				return;
			}
			System.out.println(player.getName() + " goes deeper into the woods, and notices that the spiders are becoming more plentiful the deeper they go\n"
					+ player.getName() + "ends up in a clearing, with a giant spider in front of them hanging from a spiderweb.");
			Prompt.pause();
			Battle.bossBattle(this.bossList[1]);
			spiderKilled = true;
		}
	}
}