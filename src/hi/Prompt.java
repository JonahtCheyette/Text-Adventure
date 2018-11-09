package hi;

import java.util.Scanner;

public class Prompt {
	static int choice;
	static int dDisplay;
	static String playerChoice;
	static Boolean checkChoice = false;
	static Scanner input = new Scanner(System.in);
	static Character player = GameWorld.getPlayer();

	Prompt(){
		
	}
	
	public static void setPName() {
		System.out.println("Please type character name");
		player.setName(input.nextLine());
	}
	
	public static void deathPrompt(Equipment equipment) {
		player.Die(equipment);
		System.out.println(player.getName() + " Died");
		Prompt.pause();
	}

	public static void pause() {
		System.out.println("(Enter anything to continue)");
		playerChoice = input.nextLine();
	}

	public static void yesNo(String... text) {
		while (!checkChoice) {
			if(text.length == 0) {
				System.out.println("(yes/no)");
				playerChoice = input.nextLine();
				if (Prompt.checkPChoice(checkChoice,"yes","no")) {
					checkChoice = true;
				}
			} else {
				System.out.print(text[text.length-1] + " (");
				for(int ik = 0; ik < text.length-1; ik++) {
					if (ik != 0) {
						System.out.print("/" + text[ik]);
					} else {
						System.out.print(text[ik]);
					}
				}
				System.out.print(")\n");
				playerChoice = input.nextLine();
				for(int il = 0; il < text.length-1; il++) {
					if (Prompt.checkPChoice(checkChoice,text[il])) {
						checkChoice = true;
					}
				}
			}
			if (!checkChoice) {
				System.out.println("Please enter a valid choice");
			}
		}
		checkChoice = false;
	}
	
	public static void usePrompt(Character player, String[]... choices) {
		if (choices.length > 2) {
			System.out.println("too many arguments passed to usePrompt");
		}
		dDisplay = choices.length - 1;
		while (!checkChoice) {
			System.out.print("What will " + player.getName() + " do? (");
			for (int im = 0; im < choices[dDisplay].length; im++) {
				if (choices[dDisplay][im] != null) {
					if (im != 0) {
						System.out.print("/" + choices[dDisplay][im]);
					} else {
						System.out.print(choices[dDisplay][im]);
					}
				}
			}
			System.out.print(")\n");
			playerChoice = input.nextLine();
			for (int in = 0; in < choices[0].length; in++) {
				if (choices[0][in] != null) {
					if (Prompt.checkPChoice(checkChoice,choices[0][in])) {
						choice = in;
						checkChoice = true;
					}
				}
			}
			if (!checkChoice) {
				if (Prompt.checkPChoice(checkChoice,"s")) {
					player.checkStatus();
				} else {
					System.out.println("Please enter a valid choice");
				}
			}
		}
		checkChoice = false;
	}
	
	public static int getChoice() {
		return choice;
	}

	public static String getPChoice() {
		return playerChoice;
	}

	public static void setChoice(String s) {
		playerChoice = s;
	}
	
	public static Boolean checkPChoice(Boolean checkState, String... choices) {
		checkChoice = false;
		for(int i = 0; i < choices.length; i++) {
			if(playerChoice.equalsIgnoreCase(choices[i])) {
				checkChoice = true;
			}
		}
		if(checkChoice) {
			if(!checkState) {
				checkChoice = false;
			}
			return true;
		} else {
			if(checkState) {
				checkChoice = true;
			}
			return false;
		}
	}
}
