package hi;

/**
 * TODO: fix all the prompts with 2 identical arguments
 */
import java.util.Scanner;

public class Town {
	int choice;
	String[] sellList;
	String[] choices = new String[3];
	Scanner input = new Scanner(System.in);
	Character player;
	Boss trueFinalBoss;
	Battle battle;
	Prompt prompt;

	Town(Character player, Boss trueFinalBoss, Battle battle) {
		this.player = player;
		this.trueFinalBoss = trueFinalBoss;
		this.battle = battle;
		this.prompt = new Prompt(player);
	}

	public void runTown(Equipment[] sellList, Boolean redo) {
		this.sellList = new String[sellList.length + 2];
		for (int i = 0; i < sellList.length; i++) {
			if (sellList[i] != null) {
				this.sellList[i] = sellList[i].getName();
			}
		}
		this.sellList[sellList.length] = "rest";
		this.sellList[sellList.length + 1] = "leave town";
		if (!redo) {
			System.out.println(player.getName()
					+ " walks into town and finds the nearest equipment shop, which also happens to be the town inn\n\"what can I get you?\" asks the shopkeeper.");
			prompt.pause();
		}
		for (int l = 0; l < sellList.length; l++) {
			if (sellList[l] != null) {
				System.out.println("\n" + sellList[l].getName() + " it is " + sellList[l].price + " gold");
			}
		}
		System.out.println("\nStay a night in the inn and rest\n\nLeave the town\n\n" + player.getName() + " has " + player.getGold() + " gold");
		this.choices[0] = "type what you want to buy";
		this.choices[1] = "rest";
		this.choices[2] = "leave town";
		this.prompt.usePrompt(this.sellList, this.choices);
		this.choice = this.prompt.getChoice();
		if (this.prompt.getPChoice().equalsIgnoreCase("rest")) {
			System.out.println(player.getName() + " stays at the inn and rests. When " + player.getName()
					+ " wakes up, they notice a seemingly random keyhole in the wall inscribed with the word tim above it");
			player.rest();
			if (player.getPassiveItem() != null) {
				if (player.getPassiveItem().getName().equals("Mysterious Key")) {
					System.out.println(player.getName() + " can either insert the Mysterious Key into the keyhole or leave the room.");
					this.choices[0] = "insert";
					this.choices[1] = "go back";
					this.prompt.usePrompt(this.choices);
					if (this.prompt.getPChoice().equalsIgnoreCase("insert")) {
						System.out.println("A  stone passage opens up in the wall, seemingly out of nowhere.\n" + player.getName()
								+ " walks into the passage and ends up in a giant underground colosseum\nA voice booms out:\"so, you've finally made it here...\nI bet you think you're pretty clever for figuring out my puzzle, but you don't even know who I am...\nYou see, the entire time, the true final boss wasn't the jade dragon...\nIT WAS ME! ECLIPSE!\"");
						this.prompt.pause();
						this.battle.bossBattle(player, this.trueFinalBoss);
						if (player.getHealth() <= 0 || player.prompt.getPChoice().equalsIgnoreCase("run")) {
							return;
						}
					} else if (this.prompt.getPChoice().equalsIgnoreCase("leave town")) {
						return;
					}
				}
			}
		} else if (this.prompt.getPChoice().equalsIgnoreCase("leave town")) {
			System.out.println(player.getName() + " leaves");
			return;
		} else {
			if (player.gold < sellList[this.choice].price) {
				System.out.println(player.getName() + " does not have enough money and is kicked out for window shopping");
			} else {
				System.out.println(player.getName() + " decids to buy the " + sellList[this.choice].getName());
				player.addItemToInvt(sellList[this.choice]);
				player.updateGold(-1 * sellList[this.choice].price);
				if(!player.getSlotFull()) {
					prompt.pause();
				}
			}
		}
		this.runTown(sellList, true);
	}
}