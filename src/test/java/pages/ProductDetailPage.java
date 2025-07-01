package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ui.Image;
import ui.ProductCardDetails;
import ui.TextElement;

public class ProductDetailPage extends ProductsPage {
	
	@FindBy(tagName="img")
	WebElement image;
	
	@FindBy(xpath="//div[contains(@class, 'product-information')]")
	WebElement productcarddetails;
	
	@FindBy(xpath="//div/h2[contains(@class, 'title text-center')]")
	WebElement searchProductTitle;

	public ProductDetailPage(WebDriver driver, String url) {
		super(driver, url);
	}

	public ProductDetailPage(WebDriver driver, String url, int timeout) {
		super(driver, url, timeout);
	}
	
	public Image image() {
		return new Image(driver, image, "Image product"); 
	}
	
	public ProductCardDetails productcarddetails() {
		return new ProductCardDetails(this.driver, productcarddetails, "Product card details");
	}
	
	public TextElement searchProductTitle() {
		return new TextElement(this.driver, searchProductTitle, "Search Products title");
	}

}
