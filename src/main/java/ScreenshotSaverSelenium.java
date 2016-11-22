
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
			System.out.println("0");
			driver.get(site);

			System.out.println("1");
			File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

			System.out.println("2");
			String path = outputFileDir + screenshot.getName() + ".png";

			FileUtils.copyFile(screenshot, new File(path));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void start(List<String> siteList) {

		WebDriver driver = null;
		ChromeDriverService driverService = new ChromeDriverService.Builder().usingDriverExecutable(new File(outputFileDir + "chromedriver.exe")).build();

		try {

			System.out.println("Start");
			driver = new ChromeDriver(driverService);

			for (String site : siteList) {
				takeScreenshot(driver, site);
				System.out.println("ScreenShot done [" + site + ".png]");
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
