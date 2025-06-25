package pages;
import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ui.PreliminarProductView;
import ui.TextElement;


public class ProductsPage extends BasePage {
	
	@FindBy(xpath="//div/h2[contains(@class, 'title')]")
	WebElement title;
	
	@FindBy(xpath="//div/div[contains(@class, 'product-image-wrapper')]")
	List<WebElement> product;

	public ProductsPage(WebDriver driver, String url) {
		super(driver, url);
	}
	public ProductsPage(WebDriver driver, String url, int timeout) {
		super(driver, url, timeout);
	}
	
	public List<PreliminarProductView> products(){
		List<PreliminarProductView> productViews = product.stream()
		    .map(el -> new PreliminarProductView(this.driver, el, "Card"))
		    .collect(Collectors.toList());
		return productViews;
	}
	
	public PreliminarProductView getSingleProduct(int index) {
		return products().get(index);
	}
	
	public TextElement title() {
		return new TextElement(driver, title, "Product title");
	}

}
