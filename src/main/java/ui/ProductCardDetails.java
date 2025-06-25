package ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ProductCardDetails extends Element {
	private WebElement productName;
	private By productNameLocator = By.tagName("h2");

	private WebElement category;
	private By categoryLocator = By.xpath(".//p[contains(text(),'Category')]");

	private WebElement price;
	private By priceLocator = By.xpath(".//span[text()='Rs. 500']");

	private WebElement quantityInput;
	private By quantityInputLocator = By.id("quantity");

	private WebElement addToCartBtn;
	private By addToCartBtnLocator = By.xpath(".//button[normalize-space()='Add to cart']");

	private WebElement availability;
	private By availabilityLocator = By.xpath(".//b[contains(text(),'Availability:')]");

	private WebElement condition;
	private By conditionsLocator = By.xpath(".//b[contains(text(),'Condition:')]");

	private WebElement brand;
	private By brandLocator = By.xpath(".//b[contains(text(),'Brand:')]");

	public ProductCardDetails(WebDriver driver, WebElement element, String name) {
		super(driver, element, name);
		initElements();
	}
	
	public ProductCardDetails(WebDriver driver, WebElement element, String name, int timeout) {
		super(driver, element, name, timeout);
		initElements();
	}
	
	private void initElements() {
        this.productName = element.findElement(productNameLocator);
        this.category = element.findElement(categoryLocator);
        this.price = element.findElement(priceLocator);
        this.quantityInput = element.findElement(quantityInputLocator);
        this.addToCartBtn = element.findElement(addToCartBtnLocator);
        this.availability = element.findElement(availabilityLocator);
        this.condition = element.findElement(conditionsLocator);
        this.brand = element.findElement(brandLocator);
    }
	
	public TextElement productName() {
        return new TextElement(driver, productName, "Product name Text element");
    }
	
	public TextElement category() {
        return new TextElement(driver, category, "Category Text element");
    }
	
	public TextElement price() {
        return new TextElement(driver, price, "Price Text element");
    }

    public Button addToCartBtn() {
        return new Button(driver, addToCartBtn, "Add to cart");
    }

    public InputElement quantityInput() {
        return new InputElement(driver, quantityInput, "Quantity input");
    }
    
    public TextElement availability() {
        return new TextElement(driver, availability, "Availability Text element");
    }
    
    public TextElement condition() {
        return new TextElement(driver, condition, "Conditions Text element");
    }
    
    public TextElement brand() {
        return new TextElement(driver, brand, "Brand Text element");
    }
    
    @Override
    public boolean isVisible() {
    	return (productName().isVisible() && category().isVisible() && price().isVisible() && quantityInput().isVisible() && condition().isVisible() && brand().isVisible());
    }


}
