package hi;

public class Town {
	String[] choices = new String[] {
		"Shop","Training Hall"
	};
	Adventurer player = GameWorld.getPlayer();
	Boss trueFinalBoss;

	Town(Boss trueFinalBoss) {
		this.trueFinalBoss = trueFinalBoss;
	}

	public void runTown(Equipment[] sellList, Boolean redo) {
		this.player = GameWorld.getPlayer();
		if (!redo) {
			System.out.println(player.getName() + " walks into town. there are two shops. an Equipment Shop and a trainig hall.");
		} else {
			System.out.println("Do you want to go into the shop or Training Hall?");
		}
		Prompt.usePrompt(this.player, this.choices);
		if(Prompt.getPChoice().equalsIgnoreCase("Shop")) {
			Shop.runShop(player, trueFinalBoss, sellList);
		} else {
			TrainingHall.runTrainingHall(player);
		}
		System.out.println("Do you want to leave the town?");
		Prompt.yesNo();
		if(Prompt.getPChoice().equals("no")) {
			this.runTown(sellList, true);
		} else {
			System.out.println(player.getName() + " leaves the town");
		}
	}
}