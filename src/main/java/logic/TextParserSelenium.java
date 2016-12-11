package logic;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import resources.Constants;
import resources.PropertyHolder;

public class TextParserSelenium {

	private String site = Constants.UNIQUE_TEST_URL_PATH;
	private WebDriver driver = null;
	private String text = "";
	private String uniqueValue = "";

	public TextParserSelenium() {

	}

	public void setTargetText(String text) {
		this.text = text;
		uniqueValue = "";
	}

	public void start() {

		try {

			// loading driver
			driver = new FirefoxDriver();
			// loading site
			driver.get(site);
			// set text in to text Area
			driver.findElement(By.name("user-text")).sendKeys(text);
			// click on button search
			driver.findElement(By.className("antiplagiat-form__footer-buttons")).click();
			// wait 1 hour until test ends(terminated if the test ends early)
			(new WebDriverWait(driver, PropertyHolder.getParseUniqueValueWaiting())).until(ExpectedConditions.presenceOfElementLocated(By.id("unique-value")));
			// set uniqueValue
			uniqueValue = driver.findElement(By.id("unique-value")).getText();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("timeout: - (more then " + PropertyHolder.getParseUniqueValueWaiting() + " sec)");
		} finally {
			if (driver != null) {
				driver.quit();
			}
		}
	}

	public String start(String text) {
		setTargetText(text);
		start();
		return uniqueValue;
	}

}
