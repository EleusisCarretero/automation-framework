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
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

public class BasePage {
	WebDriver driver;
	WebDriverWait wait;
	String URL;
	int TIMEOUT = 10;
	
	public BasePage(WebDriver driver, String url) {
		this.driver =  driver;
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
	
	public void clickElemet(WebElement element) {
		WebElement waitElement = null;
		try {
			waitElement = wait.until(ExpectedConditions.elementToBeClickable(element));
			waitElement.click();
		}
		catch(TimeoutException | NoSuchElementException| ElementClickInterceptedException | StaleElementReferenceException e) {
			System.out.println("Element is not clickable with timeout " + TIMEOUT + "s" + e.getMessage());
		}
	}

	public void writeElement(WebElement element, String info) {
		
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
			element.clear();
			element.sendKeys(info);
		}
		catch(TimeoutException | NoSuchElementException |StaleElementReferenceException e) {
			System.out.println("The element is not reachable. Unable to write" + e.getMessage());
		}
	}
	
	public String readTextElement(WebElement element) {
		String text = null;
		try {
			text = element.getAttribute("value");
			return text;
		}
		catch(Exception e) {
			
		}
		return text;
	}
	
	protected void selectDropDown(WebElement element, String valor, boolean usarVisibleText) throws InterruptedException {
	    try {
	        Select ddps = new Select(element);
	        if (usarVisibleText) {
	            ddps.selectByVisibleText(valor);
	        } else {
	            ddps.selectByValue(valor);
	        }
	        Thread.sleep(Duration.ofSeconds(1));
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	}

	

	protected String getDropDownValue(WebElement element) {
		Select ddps = null;
		try {
			ddps = new Select(element);
		}
		catch(Exception e) {
			
		}
		return ddps.getFirstSelectedOption().getText();
	}
	

}
