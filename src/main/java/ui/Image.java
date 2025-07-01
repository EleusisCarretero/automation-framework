package ui;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class Image extends Button {

	public Image(WebDriver driver, WebElement element, String name) {
		super(driver, element, name);
	}
	public Image(WebDriver driver, WebElement element, String name, int timeout) {
		super(driver, element, name, timeout);
	}

}
