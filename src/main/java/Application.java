import java.util.ArrayList;
import java.util.List;

public class Application {
	private static ScreenshotSaver saver;

	public static void main(String[] args) {

		// test dir
		List<String> siteList = new ArrayList<>();
		siteList.add("http://fs.to/");
		siteList.add("http://internetka.in.ua/selenium-capture-screeshot/");

		saver = new ScreenshotSaverSelenium();
		saver.start(siteList);
	}

}
