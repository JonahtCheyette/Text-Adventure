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
	Character player = GameWorld.getPlayer();
	Boss trueFinalBoss;

	Town(Boss trueFinalBoss) {
		this.trueFinalBoss = trueFinalBoss;
	}

	public void runTown(Equipment[] sellList, Boolean redo) {
		this.sellList = new String[sellList.length + 2];
		for (int io = 0; io < sellList.length; io++) {
			if (sellList[io] != null) {
				this.sellList[io] = sellList[io].getName();
			}
		}
		this.sellList[sellList.length] = "rest";
		this.sellList[sellList.length + 1] = "leave town";
		if (!redo) {
			System.out.println(player.getName()
					+ " walks into town and finds the nearest equipment shop, which also happens to be the town inn\n\"what can I get you?\" asks the shopkeeper.");
			Prompt.pause();
		}
		for (int ip = 0; ip < sellList.length; ip++) {
			if (sellList[ip] != null) {
				System.out.println("\n" + sellList[ip].getName() + " it is " + sellList[ip].price + " gold");
			}
		}
		System.out.println("\nStay a night in the inn and rest\n\nLeave the town\n\n" + player.getName() + " has " + player.getGold() + " gold");
		this.choices[0] = "type what you want to buy";
		this.choices[1] = "rest";
		this.choices[2] = "leave town";
		Prompt.usePrompt(this.player,this.sellList, this.choices);
		this.choice = Prompt.getChoice();
		if (Prompt.checkPChoice(false,"rest")) {
			System.out.println(player.getName() + " stays at the inn and rests. When " + player.getName()
					+ " wakes up, they notice a seemingly random keyhole in the wall inscribed with the word tim above it");
			player.rest();
			if (player.getPassiveItem() != null) {
				if (player.getPassiveItem().getName().equals("Mysterious Key")) {
					System.out.println(player.getName() + " can either insert the Mysterious Key into the keyhole or leave the room.");
					this.choices[0] = "insert";
					this.choices[1] = "go back";
					Prompt.usePrompt(this.player,this.choices);
					if (Prompt.checkPChoice(false,"insert")) {
						System.out.println("A  stone passage opens up in the wall, seemingly out of nowhere.\n" + player.getName()
								+ " walks into the passage and ends up in a giant underground colosseum\nA voice booms out:\"so, you've finally made it here...\nI bet you think you're pretty clever for figuring out my puzzle, but you don't even know who I am...\nYou see, the entire time, the true final boss wasn't the jade dragon...\nIT WAS ME! ECLIPSE!\"");
						Prompt.pause();
						Battle.bossBattle(this.trueFinalBoss);
						if (player.getHealth() <= 0 || Prompt.checkPChoice(false,"run")) {
							return;
						}
					} else if (Prompt.checkPChoice(false,"leave town")) {
						return;
					}
				}
			}
		} else if (Prompt.checkPChoice(false,"leave town")) {
			System.out.println(player.getName() + " leaves");
			return;
		} else {
			if (player.gold < sellList[this.choice].price) {
				System.out.println(player.getName() + " does not have enough money and is kicked out for window shopping");
			} else {
				System.out.println(player.getName() + " decids to buy the " + sellList[this.choice].getName());
				player.addItemToInvt(sellList[this.choice]);
				player.updateGold(-1 * sellList[this.choice].price);
				if(!player.getItemInSlot()) {
					Prompt.pause();
				}
			}
		}
		this.runTown(sellList, true);
	}
}