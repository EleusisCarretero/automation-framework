package pages;

import java.time.Duration;
import java.util.NoSuchElementException;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import utilities.InputElement;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class BasePage {
	WebDriver driver;
	WebDriverWait wait;
	String URL;
	int TIMEOUT = 10;
	
	public BasePage(WebDriver driver, String url) {
		this.driver =  driver;
		this.driver.manage().window().maximize();
		this.URL = url;
		wait = new WebDriverWait(this.driver, Duration.ofSeconds(TIMEOUT));
		PageFactory.initElements(this.driver, this);
	}
	
	public BasePage(WebDriver driver, String url, int timeout) {
		this.driver =  driver;
		this.URL = url;
		this.TIMEOUT = timeout;
		wait = new WebDriverWait(this.driver, Duration.ofSeconds(TIMEOUT));
		PageFactory.initElements(this.driver, this);
	}
	
	public boolean launchPage() {
		try {
			this.driver.get(this.URL);
			System.out.println(this.URL + "Correclty launched");
			return true;
		}
		catch(Exception e) {
			System.out.println("Unabel to launch page");
			return false;
		}
	}
	
	public boolean quitPage() {
		try {
			this.driver.quit();
			System.out.println(this.URL + "Corrctly quit");
			return true;
		}
		catch(Exception e) {
			System.out.println("Unabel to quite page");
			return false;
		}
	}
	public String url() {
		return URL;
	}

}
