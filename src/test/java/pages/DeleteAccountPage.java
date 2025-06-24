package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.Button;
import ui.TextElement;


public class DeleteAccountPage extends BasePage {
	
	@FindBy(xpath="//h2[contains(@class, 'title')]")
	WebElement deletedTitle;
	
	@FindBy(xpath="//div/a[@data-qa='continue-button']")
	WebElement continueBtn;

	public DeleteAccountPage(WebDriver driver, String url) {
		super(driver, url);
	}
	
	public TextElement deletedTitle() {
		return new TextElement(driver, deletedTitle, "Delete account title");
	}
	
	public Button continueBtn() {
		return new Button(driver, continueBtn, "Continue button");
	}

}
