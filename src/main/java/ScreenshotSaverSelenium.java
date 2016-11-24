
import java.io.File;
import java.util.List;

import javax.swing.filechooser.FileSystemView;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;

public class ScreenshotSaverSelenium implements ScreenshotSaver {

	// TODO load from property
	private String driverPath = getHomeDir().toString() + "\\1\\";
	// load fileSaver
	private FileSaver fileSaver = new FolderFileSaver();

	public ScreenshotSaverSelenium() {

	}

	public void start(List<String> siteList) {

		WebDriver driver = null;
		// path to chrome driver
		ChromeDriverService driverService = new ChromeDriverService.Builder().usingDriverExecutable(new File(driverPath + "chromedriver.exe")).build();

		try {

			System.out.println("Start operation");

			// Loading chrome driver
			driver = new ChromeDriver(driverService);
			// set window full screen
			driver.manage().window().maximize();

			fileSaver.createDir(siteList.get(0));

			for (String site : siteList) {

				System.out.println("_______________________________");
				System.out.println("Creating directory...");

				takeScreenshot(driver, fileSaver, site);

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

	private void takeScreenshot(WebDriver driver, FileSaver fileSaver, String site) {
		try {

			System.out.println("Loading site:[" + site + "]...");
			driver.get(site);

			System.out.println("ScreenShot creating...");
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			System.out.println("Loading ScreenShot to file dir...");

			// String path = driverPath + screenshot.getName();
			// FileUtils.copyFile(screenshot, new File(path));

			fileSaver.save(screenshot, screenshot.getName());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private File getHomeDir() {
		FileSystemView fsv = FileSystemView.getFileSystemView();
		return fsv.getHomeDirectory();
	}

}
