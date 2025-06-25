package ui;

import java.util.logging.Logger;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class SearchBar {
	protected static final Logger LOGGER = Logger.getLogger(SearchBar.class.getName());
	WebDriver driver;
	WebElement input;
	WebElement button;

	public SearchBar(WebDriver driver, WebElement input, WebElement button) {
		this.driver = driver;
		this.input = input;
		this.button = button;
	}
	
	public InputElement input() {
		return new InputElement(driver, this.input, "Search input element");
	}
	
	public Button button() {
		return new Button(driver, this.button, "Search button element");
	}
	
	public boolean search(String search, boolean clean, boolean byenter) {
		boolean status = true;
		try {
			input().wait.until(ExpectedConditions.visibilityOf(input().element));
			input().write(search,clean);
		}
		catch (TimeoutException | ElementClickInterceptedException e) {
			LOGGER.severe("Error");
			status = false; 
		}
		if(!byenter) {
			button().click();
		}
		else {
			input().element.submit();
		}
		return status;
	}

}
