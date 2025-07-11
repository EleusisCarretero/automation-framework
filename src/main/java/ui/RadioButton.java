package ui;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class RadioButton extends Button {

	public RadioButton(WebDriver driver, WebElement element, String name) {
		super(driver, element, name);
	}
	
	public RadioButton(WebDriver driver, WebElement element, String name, int timeout) {
		super(driver, element, name, timeout);
	}
	
	public void check() {
		click();
	}
	
	public boolean isChecked() {
		return element.isSelected();
	}

}
