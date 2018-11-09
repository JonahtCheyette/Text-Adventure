package hi;

import java.util.Scanner;

public class TrainingHall {
	static int choice;
	static String[] choices = new String[3];
	static Scanner input = new Scanner(System.in);
	
	TrainingHall(){
		
	}
	
	public static void runTrainingHall(Adventurer player) {
		System.out.println(player.getName() + " goes into the training hall. The owner asks \"I can teach you new skills... for 150 gold.\"\n" + player.getName() + " has " + player.getGold() + "gold.");
		Prompt.yesNo("Adventurer", "Warrior", "Druid", "What class would you like to become?");
		if(player.getGold() >= 150) {
			player.updateGold(-150);
			if(Prompt.getPChoice().equalsIgnoreCase("Adventurer")) {
				GameWorld.setPlayer("Adventurer");
				Adventurer.becomeClass(player.getName());
			} else if(Prompt.getPChoice().equalsIgnoreCase("Warrior")) {
				GameWorld.setPlayer("Warrior");
				Warrior.becomeClass(player.getName());
			} else {
				GameWorld.setPlayer("Druid");
				Druid.becomeClass(player.getName());
			}
		} else {
			System.out.println("\"I said 150 GOLD, not peanuts! get out of my Hall!\"");
		}
	}
}
