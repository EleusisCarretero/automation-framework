package ui;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class PreliminarProductView extends Element {
	private WebElement image;
	private String imgTagname = "img";
	private WebElement addcartBtn;
	private String addcartXpath = ".//a[contains(@class, 'btn') and contains(text(),'Add to cart')]";
	private WebElement viewProductBtn;
	private String viewProductXpath = ".//a[contains(@href,'/product_details/')]";

	public PreliminarProductView(WebDriver driver, WebElement element, String name){
		super(driver, element, name);
		initInnerElement();
	}

	protected PreliminarProductView(WebDriver driver, WebElement element, String name, int timeout){
		super(driver, element, name, timeout);
		initInnerElement();
	}

	private void initInnerElement() {
		try {
			this.image = this.element.findElement(By.tagName(imgTagname));
			this.addcartBtn = this.element.findElement(By.xpath(addcartXpath));
			this.viewProductBtn = this.element.findElement(By.xpath(viewProductXpath));
		} catch (NoSuchElementException e) {
			System.out.println("Uno de los elementos no se encontró en el producto: " + e.getMessage());
		}
	}

	public Image image() {
		return new Image(this.driver, this.image, "Image element");
	}

	public Button addcartBtn() {
		return new Button(this.driver, this.addcartBtn, "Add to cart Button");
	}

	public Button viewProductBtn() {
		return new Button(this.driver, this.viewProductBtn, "View product Button");
	}
	
	@Override
	public boolean isVisible() {
		return (image().isVisible() && addcartBtn().isVisible() && viewProductBtn().isVisible());
	}

}
