package utilities;

import java.time.Duration;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Element {
	protected static final Logger LOGGER = Logger.getLogger(DataBaseReader.class.getName());
	protected String name;
	protected int TIMEOUT = 10;
	protected WebDriver driver;
	protected WebElement element;
	protected WebDriverWait wait;
	protected Actions actions;
	protected String baseErrMsg = "Element " + name + " is not ";
	
	protected Element(WebDriver driver, WebElement element, String name){
		_init(driver, element, name);
	}

	protected Element(WebDriver driver, WebElement element, String name, int timeout){
		this.TIMEOUT = timeout;
		_init(driver, element, name);
	}
	
	protected void _init(WebDriver driver, WebElement element, String name) {
		this.driver = driver;
		this.name = name;
		this.element = element;
		this.wait = new WebDriverWait(this.driver, Duration.ofSeconds(TIMEOUT));
		this.actions = new Actions(this.driver);
	}

	public WebElement element() {
		return this.element;
	}
	
	public String text() {
		return element().getAttribute("value");
	}
	
	public String name() {
		return name;
	}

}
