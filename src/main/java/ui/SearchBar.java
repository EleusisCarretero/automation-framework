package ui;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;


public class SearchBar extends Element{
	
	private WebElement searchField;
	private By searchFieldLocator = By.id("search_product");
	
	private WebElement searchButton;
	private By searchButtonLocator = By.id("submit_search");

	public SearchBar(WebDriver driver, WebElement element, String name) {
		super(driver, element, name);
		initElements();
	}
	
	private void initElements() {
		this.searchField = this.element.findElement(searchFieldLocator);
		this.searchButton = this.element.findElement(searchButtonLocator);
	}
	
	public InputElement searchField() {
		return new InputElement(driver, this.searchField, "Search input element");
	}
	
	public Button searchButton() {
		return new Button(driver, this.searchButton, "Search button element");
	}
	
	public boolean search(String search, boolean clean, boolean byenter) {
		boolean status = true;
		try {
			searchField().wait.until(ExpectedConditions.visibilityOf(searchField().element));
			searchField().write(search,clean);
		}
		catch (TimeoutException | ElementClickInterceptedException e) {
			LOGGER.severe("Error");
			status = false; 
		}
		if(!byenter) {
			searchButton().click();
		}
		else {
			searchField().element.submit();
		}
		return status;
	}

}
