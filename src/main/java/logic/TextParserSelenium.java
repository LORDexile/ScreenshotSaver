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

	private void loading(WebDriver driver) {

		try {
			// loading site
			driver.get(site);

		} catch (Exception e) {
			System.out.println("loading error");
		}

		try {
			if (driver.findElement(By.xpath("//button[contains(text(),'Войти')]")).isDisplayed()) {

				driver.findElement(By.name("email")).sendKeys(PropertyHolder.getParseTextRuLogin());
				driver.findElement(By.name("password")).sendKeys(PropertyHolder.getParseTextRuPassword());
				driver.findElement(By.xpath("//button[contains(text(),'Войти')]")).click();
			}
		} catch (Exception e) {
			System.out.println("login error");
		}

		try {
			// Waiting needed elements
			(new WebDriverWait(driver, 5)).until(ExpectedConditions.presenceOfElementLocated(By.className("header__account")));
			// set text in to text Area
			driver.findElement(By.name("user-text")).sendKeys(text);// editor__input
			// click on button search
			driver.findElement(By.className("antiplagiat-form__footer-buttons")).click();// editor__act-start-check

		} catch (Exception e) {
			System.out.println("sending text error");
		}
		try {
			// set text in to text Area
			driver.findElement(By.id("editor__input")).sendKeys(text);
			Thread.sleep(1000);
			// click on button search
			driver.findElement(By.id("editor__act-start-check")).click();

		} catch (Exception e) {
			System.out.println("sending text error2");
		}

		try {
			// wait until error displayed
			(new WebDriverWait(driver, 7)).until(ExpectedConditions.presenceOfElementLocated(By.className("error-limit__title")));

			// if error displayed wait 4 min, and try again
			if (driver.findElement(By.className("error-limit__title")).isDisplayed()) {

				System.out.println("found error limit, processing...");

				System.out.println("wating 1/4 min");
				Thread.sleep(60 * 1000);
				System.out.println("wating 2/4 min");
				Thread.sleep(60 * 1000);
				System.out.println("wating 3/4 min");
				Thread.sleep(60 * 1000);
				System.out.println("wating 4/4 min");
				Thread.sleep(60 * 1000);

				System.out.println("reloading...");
				driver.quit();

				start();

				System.out.println("ok");

				return;

			}
		} catch (InterruptedException ex) {
			System.out.println("thread exception");
		} catch (Exception e) {
			System.out.println("no error");
		}

		// wait 1 hour until test ends(terminated if the test ends early)
		(new WebDriverWait(driver, PropertyHolder.getParseUniqueValueWaiting())).until(ExpectedConditions.presenceOfElementLocated(By.id("unique-value")));
		// set uniqueValue
		uniqueValue = driver.findElement(By.id("unique-value")).getText();
		System.out.println(uniqueValue);

	}

	public void start() {

		try {
			// loading driver
			driver = new FirefoxDriver();

			loading(driver);

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
