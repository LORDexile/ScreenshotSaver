package logic;

import controller.MainMenuController;

public class Application {

	public static void main(String[] args) {

		PropetiesWorker worker = new PropetiesWorker();

		MainMenuController controller = new MainMenuController();
		controller.showManeMenu();

	}

}
