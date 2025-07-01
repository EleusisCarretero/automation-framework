package ui;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;


public class DropDownElement extends Element {

	public DropDownElement(WebDriver driver, WebElement element, String name) {
		super(driver, element, name);
	}
	
	public DropDownElement(WebDriver driver, WebElement element, String name, int timeout) {
		super(driver, element, name, timeout);
	}
	
	public void set(String value, boolean byVisibleText) {
		Select select = new Select(this.element);
		if (byVisibleText) {
			select.selectByVisibleText(value);
		}
		else {
			select.selectByValue(value);
		}	
	}

	public void set(int value) {
		Select select = new Select(this.element);
		select.selectByIndex(value);
	}
	
	public String get(boolean getText) {
		Select select = new Select(this.element);
		if(getText) {
			return select.getFirstSelectedOption().getText();
		}
		return select.getFirstSelectedOption().getAttribute("value");
	}
	
	

}
