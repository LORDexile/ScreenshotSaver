package logic;

import java.util.ArrayList;
import java.util.List;

import controller.MainMenuController;

public class Application {
	private static ScreenshotSaver saver;

	public static void main(String[] args) {
		// test dir
		boolean a = false;
		boolean b = false;

		if (b) {
			List<String> siteList = new ArrayList<>();
			siteList.add("https://dengarden.com/");
			// siteList.add("v");

			saver = new ScreenshotSaverSelenium();
			saver.start(siteList);
		}

		if (a) {
			SiteParserExtend parser = new ArchiveOrgSiteParser();
			parser.setTargetSite("dengarden.com");
			parser.parse();

			List<String> list = new ArrayList<>();
			list = parser.getTargetSiteList();

			int i = 1;
			for (String str : list) {
				System.out.println("[" + i++ + "] - " + str);

			}
			saver = new ScreenshotSaverSelenium();
			saver.start(list);

			System.out.println("END");
		}

		MainMenuController controller = new MainMenuController();
		controller.showManeMenu();
		// String site =
		// "https://web.archive.org/web/20160812193103/https://dengarden.com/";

	}

}
