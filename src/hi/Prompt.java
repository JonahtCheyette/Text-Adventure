package hi;

import java.util.Scanner;

public class Prompt {
	int choice;
	int dDisplay;
	String playerChoice;
	Boolean checkChoice = false;
	Boolean town = false;
	Character player;
	Scanner input = new Scanner(System.in);

	Prompt(Character player) {
		this.player = player;
	}

	public void deathPrompt() {
		this.player.Die();
		System.out.println(this.player.getName() + " Died");
		this.pause();
	}

	public void pause() {
		System.out.println("(Enter anything to continue)");
		this.playerChoice = input.nextLine();
	}

	public void yesNo(String... text) {
		while (!this.checkChoice) {
			if(text.length == 0) {
				System.out.println("(yes/no)");
				this.playerChoice = input.nextLine();
				if (this.playerChoice.equalsIgnoreCase("yes") || this.playerChoice.equalsIgnoreCase("no")) {
					this.checkChoice = true;
				}
			} else {
				System.out.print(text[text.length-1] + " (");
				for(int i = 0; i < text.length-1; i++) {
					if (i != 0) {
						System.out.print("/" + text[i]);
					} else {
						System.out.print(text[i]);
					}
				}
				System.out.print(")\n");
				this.playerChoice = input.nextLine();
				for(int i = 0; i < text.length-1; i++) {
					if (this.playerChoice.equalsIgnoreCase(text[i])) {
						this.checkChoice = true;
					}
				}
			}
			if (!this.checkChoice) {
				System.out.println("Please enter a valid choice");
			}
		}
		this.checkChoice = false;
	}
	
	public void usePrompt(String[]... choices) {
		this.dDisplay = choices.length - 1;
		if (choices.length > 2) {
			System.out.println("too many arguments passed to usePrompt");
		}
		while (!this.checkChoice) {
			System.out.print("What will " + this.player.getName() + " do? (");
			for (int io = 0; io < choices[this.dDisplay].length; io++) {
				if (choices[this.dDisplay][io] != null) {
					if (io != 0) {
						System.out.print("/" + choices[this.dDisplay][io]);
					} else {
						System.out.print(choices[this.dDisplay][io]);
					}
				}
			}
			System.out.print(")\n");
			this.playerChoice = input.nextLine();
			for (int io = 0; io < choices[0].length; io++) {
				if (choices[0][io] != null) {
					if (this.playerChoice.equalsIgnoreCase(choices[0][io])) {
						this.choice = io;
						this.checkChoice = true;
					}
				}
			}
			if (!this.checkChoice) {
				if (playerChoice.equalsIgnoreCase("s")) {
					player.checkStatus();
				} else {
					System.out.println("Please enter a valid choice");
				}
			}
		}
		this.checkChoice = false;
	}
	
	public int getChoice() {
		return this.choice;
	}

	public String getPChoice() {
		return this.playerChoice;
	}

	public void setChoice(String s) {
		this.playerChoice = s;
	}
}