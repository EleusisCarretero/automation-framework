package ui;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TextElement extends Element{

	public TextElement(WebDriver driver, WebElement element, String name) {
		super(driver, element, name);
	}

	public TextElement(WebDriver driver, WebElement element, String name, int timeout) {
		super(driver, element, name, timeout);
	}
	
	public String text() {
		return element().getText();
	}

}
