package utilities;
import java.util.NoSuchElementException;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InputElement extends Element{

	public InputElement(WebDriver driver, WebElement element, String name) {
		super(driver, element, name);
	}
	
	public InputElement(WebDriver driver, WebElement element, String name, int timeout) {
		super(driver, element, name, timeout);
	}
	
	private void _commonwrite() {
		try {
			wait.until(ExpectedConditions.visibilityOf(element));
		}
		catch(NoSuchElementException | TimeoutException e) {
			LOGGER.severe(baseErrMsg + "visible" + "within timeout" + TIMEOUT + "s" + e.getMessage());
		}
	}
	
	public void write(String input, boolean clean) {
		_commonwrite();
		if(clean) {this.element().clear();}
		this.element().sendKeys(input);
	}
	

}
