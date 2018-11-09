package hi;

public class Town {
	Character player = GameWorld.getPlayer();
	Boss trueFinalBoss;

	Town(Boss trueFinalBoss) {
		this.trueFinalBoss = trueFinalBoss;
	}

	public void runTown(Equipment[] sellList, Boolean redo) {
		this.player = GameWorld.getPlayer();
		if (!redo) {
			System.out.println(player.getName()
					+ " walks into town and finds the nearest equipment shop, which also happens to be the town inn\n\"what can I get you?\" asks the shopkeeper.");
			Prompt.pause();
		}
		Shop.runShop(player, trueFinalBoss, sellList);
		System.out.println("Do you want to leave the town?");
		Prompt.yesNo();
		if(Prompt.getPChoice().equals("no")) {
			this.runTown(sellList, true);
		} else {
			System.out.println(player.getName() + " leaves the town");
		}
	}
}