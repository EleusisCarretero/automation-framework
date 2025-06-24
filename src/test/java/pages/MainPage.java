package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.Button;
import ui.TextElement;


public class MainPage extends BasePage {
	
	@FindBy(xpath="//header[@id='header']//li[10]//a[1]")
	WebElement loggedTextElement;
	
	@FindBy(xpath="//header[@id='header']//li[5]//a[1]")
	WebElement deleteAccBtn;
	
	@FindBy(xpath="//header[@id='header']//li[4]//a[1]")
	WebElement logoutBtn;

	public MainPage(WebDriver driver, String url) {
		super(driver, url);
	}
	
	public TextElement loggedTextElement() {
		return new TextElement(driver, loggedTextElement, "Logged text element");
	}
	
	public Button deleteAccBtn() {
		return new Button(driver, deleteAccBtn, "Delete account Button");
	}
	
	public Button logoutBtn() {
		return new Button(driver, logoutBtn, "Logout Button");
	}
}
