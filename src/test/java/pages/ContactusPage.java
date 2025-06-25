package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.Button;
import ui.InputElement;
import ui.TextElement;
import ui.Upload;


public class ContactusPage extends BasePage {

	public ContactusPage(WebDriver driver, String url) {
		super(driver, url);
	}
	public ContactusPage(WebDriver driver, String url, int timeout) {
		super(driver, url, timeout);
	}
	
	@FindBy(xpath="//*[@id='contact-page']/div[1]/div/div/h2")
	WebElement contactUsTitle;
	
	@FindBy(xpath="//*[@id='contact-page']/div[2]/div[1]/div/h2")
	WebElement getintouchTitle;
	
	@FindBy(xpath="//input[@type='text' and @name='name']")
	WebElement nameInput;
	
	@FindBy(xpath="//input[@type='email' and @name='email']")
	WebElement emailInput;
	
	@FindBy(xpath="//input[@type='text' and @name='subject']")
	WebElement subjectInput;
	
	@FindBy(xpath="//textarea[@name='message' and @id='message']")
	WebElement messageInput;
	
	@FindBy(xpath="//input[@type='file']")
	WebElement choofileUpload;
	
	@FindBy(xpath="//input[@type='submit']")
	WebElement submitBtn;
	
	@FindBy(xpath="//div/div[contains(@class,'status')]")
	WebElement scuccessText;
	
	public InputElement contactUsTitle() {
		return new InputElement(this.driver, contactUsTitle, "Contact Us Title text");
	}
	
	public InputElement getintouchTitle() {
		return new InputElement(this.driver, getintouchTitle, "Get in touch text message");
	}
	
	public InputElement nameInput() {
		return new InputElement(this.driver, nameInput, "Input name element");
	}
	
	public InputElement emailInput() {
		return new InputElement(this.driver, emailInput, "Input email element");
	}
	
	public InputElement subjectInput() {
		return new InputElement(this.driver, subjectInput, "Input subject element");
	}
	
	public InputElement messageInput() {
		return new InputElement(this.driver, messageInput, "Input message element");
	}
	
	public Upload choofileUpload() {
		return new Upload(this.driver, choofileUpload, "Upload element choose file");
	}
	
	public Button submitBtn() {
		return new Button(this.driver, submitBtn, "Button element Submit");
	}
	
	public TextElement scuccessText() {
		return new TextElement(driver, scuccessText, "Success text element");
	}

}
