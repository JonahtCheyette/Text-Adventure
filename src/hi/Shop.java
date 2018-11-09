package hi;

import java.util.Scanner;

public class Shop {
	static int choice;
	static String[] sellList;
	static String[] choices = new String[3];
	static Scanner input = new Scanner(System.in);
	
	Shop(){
		
	}
	
	public static void runShop(Character player, Boss trueFinalBoss, Equipment[] sell) {
		sellList = new String[sell.length + 2];
		System.out.println(sellList.length);
		for (int io = 0; io < sell.length; io++) {
			if (sell[io] != null) {
				sellList[io] = sell[io].getName();
			}
		}
		sellList[sell.length] = "rest";
		sellList[sell.length + 1] = "leave town";
		for (int ip = 0; ip < sell.length; ip++) {
			if (sellList[ip] != null) {
				System.out.println("\n" + sell[ip].getName() + " it is " + sell[ip].price + " gold");
			}
		}
		System.out.println("\nStay a night in the inn and rest\n\nLeave the town\n\n" + player.getName() + " has " + player.getGold() + " gold");
		choices[0] = "type what you want to buy";
		choices[1] = "rest";
		choices[2] = "leave town";
		Prompt.usePrompt(player, sellList, choices);
		choice = Prompt.getChoice();
		if (Prompt.checkPChoice(false,"rest")) {
			System.out.println(player.getName() + " stays at the inn and rests. When " + player.getName()
					+ " wakes up, they notice a seemingly random keyhole in the wall inscribed with the word tim above it");
			player.rest();
			if (player.getPassiveItem() != null) {
				if (player.getPassiveItem().getName().equals("Mysterious Key")) {
					System.out.println(player.getName() + " can either insert the Mysterious Key into the keyhole or leave the room.");
					choices[0] = "insert";
					choices[1] = "go back";
					Prompt.usePrompt(player,choices);
					if (Prompt.checkPChoice(false,"insert")) {
						System.out.println("A  stone passage opens up in the wall, seemingly out of nowhere.\n" + player.getName()
								+ " walks into the passage and ends up in a giant underground colosseum\nA voice booms out:\"so, you've finally made it here...\nI bet you think you're pretty clever for figuring out my puzzle, but you don't even know who I am...\nYou see, the entire time, the true final boss wasn't the jade dragon...\nIT WAS ME! ECLIPSE!\"");
						Prompt.pause();
						Battle.bossBattle(trueFinalBoss);
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
			if (player.gold < sell[choice].price) {
				System.out.println(player.getName() + " does not have enough money and is kicked out for window shopping");
			} else {
				System.out.println(player.getName() + " decids to buy the " + sell[choice].getName());
				player.addItemToInvt(sell[choice]);
				player.updateGold(-1 * sell[choice].price);
				if(!player.getItemInSlot()) {
					Prompt.pause();
				}
			}
		}
		Shop.runShop(player, trueFinalBoss, sell);
	}
}
