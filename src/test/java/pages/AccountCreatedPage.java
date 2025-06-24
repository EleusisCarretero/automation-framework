package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.Button;
import ui.TextElement;


public class AccountCreatedPage extends BasePage {
	
	@FindBy(xpath="//h2[contains(@class, 'title')][1]")
	WebElement titleIntoText;
	
	@FindBy(xpath="//div/a[contains(@class, 'btn')]")
	WebElement continueBtn;
	

	public AccountCreatedPage(WebDriver driver, String url) {
		super(driver, url);
	}
	
	public TextElement titleIntoText() {
		return new TextElement(driver, titleIntoText, "Title text element");
	}
	
	public Button continueBtn() {
		return new Button(driver, continueBtn, "Continue Button");
	}

}
