package ui;
import java.time.Duration;
import java.util.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class Element {
	protected static final Logger LOGGER = Logger.getLogger(Element.class.getName());
	protected String name;
	protected int TIMEOUT = 10;
	protected WebDriver driver;
	protected WebElement element;
	public WebDriverWait wait;
	protected Actions actions;
	protected String baseErrMsg = "Element " + name + " is not ";
	
	public Element() {
		
	}
	
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
	
	public boolean dinamicText(String baseLocator, String expectedMessage) {
		boolean hasActualText = false;
		try {
			String wholeLocator = String.format(baseLocator, expectedMessage);
			WebElement element = driver.findElement(By.xpath(wholeLocator));
			this.wait.until(ExpectedConditions.visibilityOf(element));
			hasActualText = true;
		}
		catch(NoSuchElementException e) {
			LOGGER.severe("No such element");
		}
		return hasActualText;
	}
	
	public void moveOn() throws ElementException {
		try {
			this.wait.until(ExpectedConditions.visibilityOf(element));
			this.actions.moveToElement(this.element);
		}
		catch(NoSuchElementException | TimeoutException e) {
			LOGGER.severe("No such element or it is not visible within the timeout " + e.getMessage());
			throw new ElementException("Unrachable element");
		}
	}
	
	public boolean isVisible() {
		boolean isVisible = true;
		try {
			this.wait.until(ExpectedConditions.visibilityOf(element));
		}
		catch(NoSuchElementException | TimeoutException e) {
			LOGGER.severe("Elemnt is not visible within the timeout " + e.getMessage());
			isVisible = false;
		}
		return isVisible;
	}

}
