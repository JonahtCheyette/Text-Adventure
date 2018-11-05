package hi;

import java.util.Scanner;

public class Prompt {
	int choice;
	int dDisplay;
	String playerChoice;
	Boolean checkChoice = false;
	Boolean town = false;
	Character player = GameWorld.player();
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
				for(int ik = 0; ik < text.length-1; ik++) {
					if (ik != 0) {
						System.out.print("/" + text[ik]);
					} else {
						System.out.print(text[ik]);
					}
				}
				System.out.print(")\n");
				this.playerChoice = input.nextLine();
				for(int il = 0; il < text.length-1; il++) {
					if (this.playerChoice.equalsIgnoreCase(text[il])) {
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
			for (int im = 0; im < choices[this.dDisplay].length; im++) {
				if (choices[this.dDisplay][im] != null) {
					if (im != 0) {
						System.out.print("/" + choices[this.dDisplay][im]);
					} else {
						System.out.print(choices[this.dDisplay][im]);
					}
				}
			}
			System.out.print(")\n");
			this.playerChoice = input.nextLine();
			for (int in = 0; in < choices[0].length; in++) {
				if (choices[0][in] != null) {
					if (this.playerChoice.equalsIgnoreCase(choices[0][in])) {
						this.choice = in;
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
