package hi;

import java.util.Arrays;

/**
 * TODO: Finish multiline logic
 */

import java.util.Scanner;

public class Prompt {
	static int dDisplay;
	static int runThrough;
	static int correctCount;
	static int amountWrong;
	static int fWrong;
	static int[] choice = new int[21];
	static int[] whereCommas = new int[21];
	static String playerChoice;
	static Boolean slotTaken = false;
	static Boolean checkChoice = false;
	static Boolean[] whichRight = new Boolean[21];
	static Scanner input = new Scanner(System.in);
	static Adventurer player = GameWorld.getPlayer();

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
	
	public static void usePrompt(Adventurer player, String[]... choices) {
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
						checkChoice = true;
					}
				}
			}
			if (Prompt.checkPChoice(checkChoice, "s")) {
				player.checkStatus();
			} else if(!checkChoice){
				System.out.println("Please enter a valid choice");
			}
		}
		checkChoice = false;
	}
	
	public static void shopPrompt(Adventurer player, String[]... choices) {
		runThrough = 0;
		amountWrong = 0;
		correctCount = 0;
		fWrong = 0;
		choice = new int[21];
		slotTaken = false;
		Arrays.fill(whichRight, false);
		if (choices.length > 2) {
			System.out.println("too many arguments passed to townPrompt");
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
			for(int io = 0; io < whereCommas.length; io++) {
				whereCommas[io] = -1;
			}
			for (int io = 0; io < playerChoice.length(); io++){
				if(playerChoice.substring(io, io+1).equalsIgnoreCase(",")) {
					whereCommas[runThrough] = io;
					runThrough++;
				}
			}
			if(runThrough > 0) {
				for(int io = 0; io < runThrough + 1; io++) {
					for (int ii = 0; ii < choices[0].length; ii++) {
						if (choices[0][ii] != null && !slotTaken) {
							if(io == 0) {
								if(playerChoice.substring(0, whereCommas[io]).replace(" ", "").replace(",", "").equalsIgnoreCase(choices[0][ii].replace(" ", ""))) {
									choice[correctCount] = ii;
									correctCount++;
									checkChoice = true;
									whichRight[io] = true;
									slotTaken = true;
								} else {
									fWrong = io;
								}
							} else if (io == runThrough) {
								if(playerChoice.substring(whereCommas[io - 1]).replace(" ", "").replace(",", "").equalsIgnoreCase(choices[0][ii].replace(" ", ""))) {
									choice[correctCount] = ii;
									correctCount++;
									checkChoice = true;
									whichRight[io] = true;
									slotTaken = true;
								} else {
									fWrong = io;
								}
							} else {
								if(playerChoice.substring(whereCommas[io - 1], whereCommas[io]).replace(" ", "").replace(",", "").equalsIgnoreCase(choices[0][ii].replace(" ", ""))) {
									choice[correctCount] = ii;
									correctCount++;
									checkChoice = true;
									whichRight[io] = true;
									slotTaken = true;
								} else {
									fWrong = io;
								}
							}
						}
					}
					slotTaken = false;
				}
				if (Prompt.checkPChoice(checkChoice, "s")) {
					player.checkStatus();
				}
				for(int io = 0; io < runThrough+1; io++) {
					if(!whichRight[io]) {
						amountWrong++;
					}
				}
				if (amountWrong > 0) {
					System.out.print("Your ");
					for(int io = 0; io < runThrough + 1; io++) {
						if(io == fWrong && amountWrong > 1) {
							System.out.print("and ");
						}
						if(!whichRight[io]) {
							System.out.print(io + 1);
							if (io == 0) {
								System.out.print("st");
							} else if (io == 1) {
								System.out.print("nd");
							} else if (io == 2) {
								System.out.print("rd");
							} else {
								System.out.print("th");
							}
							if(io != fWrong || amountWrong == 2) {
								System.out.print(",");
							}
							System.out.print(" ");
						}
					}
					if(amountWrong == 1) {
						System.out.print("input is");
					} else {
						System.out.print("inputs are");
					}
					System.out.print(" invalid. All the rest have been processed\n");
				}
			} else {
				for (int in = 0; in < choices[0].length; in++) {
					if (choices[0][in] != null) {
						if (Prompt.checkPChoice(checkChoice,choices[0][in])) {
							choice[0] = in;
							checkChoice = true;
							correctCount = 1;
						}
					}
				}
			}
			if (Prompt.checkPChoice(checkChoice, "s")) {
				player.checkStatus();
			}
		}
		checkChoice = false;
	}
	
	public static int getCorrectCount() {
		return correctCount;
	}
	
	public static int[] getChoice() {
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