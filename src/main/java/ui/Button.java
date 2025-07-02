package ui;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class Button extends Element {

	public Button(WebDriver driver, WebElement element, String name) {
		super(driver, element, name);
	}
	
	public Button(WebDriver driver, WebElement element, String name, int timeout) {
		super(driver, element, name, timeout);
	}

	private void _commonClick() {
		try {
			this.wait.until(ExpectedConditions.and(
					ExpectedConditions.visibilityOf(element),
					ExpectedConditions.elementToBeClickable(element)
					));
		}
		catch(NoSuchElementException | StaleElementReferenceException e) {
			LOGGER.severe(baseErrMsg + "reachable " + "within timeout" + TIMEOUT + "s" + e.getMessage());
		}
		catch(TimeoutException | ElementClickInterceptedException e) {
			LOGGER.severe(baseErrMsg + "clickable " + "within timeout" + TIMEOUT + "s" + e.getMessage());
		}
		this.actions.moveToElement(element).perform();
		
	}
		
	public void click() {
		_commonClick();
		this.element.click();
	}
	
	public void longclick(int holdTime) {
		_commonClick();
		this.actions.clickAndHold().perform();
		try {
			Thread.sleep(Duration.ofSeconds(holdTime).toMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.actions.release().perform();
	}
	
	public void longclick(long holdTime) {
		_commonClick();
		this.actions.clickAndHold().perform();
		try {
			Thread.sleep(Duration.ofMillis(holdTime).toMillis());
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		this.actions.release().perform();
	}
	
	public void dragAndDrop(WebElement destination) {
		this.actions.moveToElement(element).perform();
		_commonClick();
		this.actions
		.clickAndHold(element)
		.moveToElement(destination)
		.build()
		.perform();
	}
	
	public void dragAndDrop(int xCo, int yCo) {
		this.actions.moveToElement(element).perform();
		_commonClick();
		this.actions
		.clickAndHold(element)
		.moveToLocation(xCo, yCo)
		.build()
		.perform();
	}
	
	public void clickAndHold(Runnable runable) {
		this.actions.moveToElement(element).perform();
		_commonClick();
		this.actions.clickAndHold(element).perform();
		try {
			runable.run();
		}
		finally {
		this.actions.release().perform();
		}
	}
	
	public <T> void clickAndHold(T param, Consumer<T> runable) {
		this.actions.moveToElement(element).perform();
		_commonClick();
		this.actions.clickAndHold(element).perform();
		try {
			runable.accept(param);
		}
		finally {
		this.actions.release().perform();
		}
	}

	public void moveOn() {
		try {
			super.moveOn();
		}
		catch(ElementException e) {
			LOGGER.severe("Unable to move over Button element "+ name);
		}
	}
}
