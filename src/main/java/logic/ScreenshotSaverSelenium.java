package logic;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import interfaces.FileSaver;
import interfaces.ScreenshotSaver;

public class ScreenshotSaverSelenium implements ScreenshotSaver {

	// load fileSaver
	private FileSaver fileSaver;

	public ScreenshotSaverSelenium(String saveDirPath) {
		fileSaver = new FolderFileSaver(saveDirPath);
	}

	public void start(Map<String, ArrayList<String>> siteMap) {

		WebDriver driver = null;

		try {

			driver = new FirefoxDriver();

			// set window full screen
			driver.manage().window().maximize();
			// Set timeout for loading page (20 second)
			driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);

			for (Map.Entry<String, ArrayList<String>> item : siteMap.entrySet()) {

				for (String site : item.getValue()) {

					// loading site
					try {
						driver.get(site);
					} catch (TimeoutException e) {
						System.out.println("loading brack! timeout > 20 sec");
					}

					// take & save screenshot
					takeScreenshot(driver, fileSaver, item.getKey());

				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}

	}

	private void takeScreenshot(WebDriver driver, FileSaver fileSaver, String siteName) {
		try {

			// Creating directory
			fileSaver.createDir(siteName);

			// ScreenShot creating
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			// screenshot saving
			fileSaver.save(screenshot, screenshot.getName());

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("error! site: [" + driver.getCurrentUrl() + "]");
		}
	}

}
