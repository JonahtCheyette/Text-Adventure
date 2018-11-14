package hi;

import java.util.Scanner;

public class Shop {
	static String[] sellList;
	static String[] choices = new String[3];
	static Scanner input = new Scanner(System.in);
	
	Shop(){
		
	}
	
	public static void runShop(Adventurer player, Boss trueFinalBoss, Equipment[] sell) {
		sellList = new String[sell.length + 2];
		for (int io = 0; io < sell.length; io++) {
			if (sell[io] != null) {
				sellList[io] = sell[io].getName();
			}
		}
		sellList[sell.length] = "rest";
		sellList[sell.length + 1] = "leave";
		for (int ip = 0; ip < sell.length; ip++) {
			if (sellList[ip] != null) {
				System.out.println("\n" + sell[ip].getName() + " it is " + sell[ip].price + " gold");
			}
		}
		System.out.println("\nStay a night in the inn and rest\n\nLeave the town\n\n" + player.getName() + " has " + player.getGold() + " gold");
		choices[0] = "type what you want to buy";
		choices[1] = "rest";
		choices[2] = "leave";
		Prompt.shopPrompt(player, sellList, choices);
		if (Prompt.checkPChoice(false, "rest")) {
			System.out.println(player.getName() + " stays at the inn and rests. When " + player.getName()
					+ " wakes up, they notice a seemingly random keyhole in the wall inscribed with the word tim above it");
			player.rest();
			if (player.getPassiveItem() != null && player.getPassiveItem().getName().equals("Mysterious Key")) {
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
			} else {
				Prompt.pause();
			}
		} else if (Prompt.checkPChoice(false, "leave")) {
			System.out.println(player.getName() + " leaves the shop");
			return;
		} else {
			for(int io = 0; io < Prompt.getCorrectCount(); io++) {
				if (player.gold < sell[Prompt.getChoice()[io]].price) {
					System.out.println(player.getName() + " does not have enough money for the " + sell[Prompt.getChoice()[io]].getName());
				} else {
					System.out.println(player.getName() + " decides to buy the " + sell[Prompt.getChoice()[io]].getName());
					player.addItemToInvt(sell[Prompt.getChoice()[io]]);
					player.updateGold(-1 * sell[Prompt.getChoice()[io]].price);
					if(!player.getItemInSlot() && Prompt.getCorrectCount() == 1) {
						Prompt.pause();
					}
				}
			}
		}
		Shop.runShop(player, trueFinalBoss, sell);
	}
}
