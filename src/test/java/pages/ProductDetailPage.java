package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import ui.Image;
import ui.ProductCardDetails;

public class ProductDetailPage extends ProductsPage {
	
	@FindBy(tagName="img")
	WebElement image;
	
	@FindBy(xpath="//div[contains(@class, 'product-information')]")
	WebElement productcarddetails;
	

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

}
