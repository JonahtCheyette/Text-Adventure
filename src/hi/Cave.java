package hi;

import java.util.Scanner;

public class Cave {
	String[] choices = new String[2];
	Boolean open = false;
	Boolean caveDragonKilled = false;
	Boolean gameWon = false;
	Scanner input = new Scanner(System.in);
	Character player = GameWorld.getPlayer();
	Boss caveDragon;
	Boss finalBoss;
	Boss[] bossList = new Boss[1];
	Mob[] enemyList = new Mob[5];

	Cave(Boss[] bossList, Mob[] enemyList, Boss finalBoss) {
		this.bossList = bossList;
		this.enemyList = enemyList;
		this.finalBoss = finalBoss;
		this.open = false;
	}

	public Boolean caveComplete() {
		if (this.caveDragonKilled) {
			return true;
		} else {
			return false;
		}
	}

	public Boolean gameFinished() {
		if (this.gameWon) {
			return true;
		} else {
			return false;
		}
	}

	public void openDoor() {
		this.open = true;
	}

	public void playerDied() {
		this.open = false;
	}

	public void runCave() {
		this.finalBoss.reset();
		for (int z = 0; z < this.bossList.length; z++) {
			this.bossList[z].reset();
		}
		for (int a = 0; a < this.enemyList.length; a++) {
			this.enemyList[a].reset();
		}
		Battle.encounters(this.enemyList, "cave");
		if (player.getHealth() <= 0 || Prompt.checkPChoice(false,"run","stop")) {
			return;
		}
		if (this.open) {
			System.out
					.println(player.getName() + " decides to go into the cave. To their right is a giant door\nyou can either go into the door, or go farther in.");
			choices[0] = "door";
			choices[1] = "farther";
			Prompt.usePrompt(player,this.choices);
			if (Prompt.getPChoice().equalsIgnoreCase("door")) {
				System.out.println(player.getName()
						+ " decides to go through the door, and ends up in a giant cavern full of jade\n directly ahead of them is a sleeping dragon covered in jade cyrstals\nas they walk forwards the dragon raises it's head and roars!");
				Prompt.pause();
				Battle.bossBattle(this.finalBoss);
				if (this.player.getHealth() <= 0) {
					return;
				} else {
					this.gameWon = true;
					System.out.println("YOU WIN!!!!!");
					return;
				}
			}
			System.out.println(player.getName()
					+ " decides to go deeper into the cave. As you walk farther in, the air becomes dryer, and it begins to get darker\nafter a while, they come across a sleeping dragon. \nyou can either decide to sneak away or attack");
		} else {
			System.out.println(player.getName()
					+ " decides to go into the cave. To their right is a giant closed door with a picture of a spider, dragon, and troll on it\nafter walking past this oddity, you come across a sleeping dragon. \nyou can either decide to sneak away or attack");
		}
		System.out.println(player.getName() + " can either sneak away or attack.");
		choices[0] = "sneak";
		choices[1] = "attack";
		Prompt.usePrompt(player,choices);
		if (Prompt.checkPChoice(false,"sneak")) {
			if (Math.random() > 0.5) {
				System.out.println(
						"as " + player.getName() + " is sneaking away they accidentally knock a pebble that rolls and hits the dragon.\nit wakes up and eyes "
								+ player.getName() + " hungrily");
			} else {
				return;
			}
		}
		if (Prompt.checkPChoice(false,"attack")) {
			System.out.println("you attack the dragon and deal 50 damage");
			this.bossList[0].takeDmg(50);
		}
		Prompt.pause();
		Battle.bossBattle(this.bossList[0]);
		this.caveDragonKilled = true;
	}
}