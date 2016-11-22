
import java.io.File;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

public class ScreenshotSaverSelenium implements ScreenshotSaver {
	private String outputFileDir;

	public ScreenshotSaverSelenium() {
		outputFileDir = getHomeDir().toString() + "\\1\\";
	}

	public void setOutputFileDir(String outputFilePath) {
		this.outputFileDir = outputFilePath;
	}

	private static File getHomeDir() {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		return fsv.getHomeDirectory();
	}

	private void takeScreenshot(WebDriver driver, String site) {
		try {

			System.out.println("Loading site:[" + site + "]...");
			driver.get(site);

			System.out.println("ScreenShot creating...");
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			System.out.println("Loading ScreenShot to file dir...");
			String path = outputFileDir + screenshot.getName();

			FileUtils.copyFile(screenshot, new File(path));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start(List<String> siteList) {

		WebDriver driver = null;
		// path to chrome driver
		ChromeDriverService driverService = new ChromeDriverService.Builder().usingDriverExecutable(new File(outputFileDir + "chromedriver.exe")).build();

		try {

			System.out.println("Start operation");

			// Loading chrome driver
			driver = new ChromeDriver(driverService);
			// set window full screen
			driver.manage().window().maximize();

			for (String site : siteList) {

				System.out.println("_______________________________");

				takeScreenshot(driver, site);

				System.out.println("Operation anded.");
				System.out.println("___________________________");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (driver != null) {
				driver.close();
			}
		}
	}

}
