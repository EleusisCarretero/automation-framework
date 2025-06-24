package pages;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import ui.Button;
import ui.InputElement;
import ui.TextElement;


public class LoginPage extends BasePage {
	
	// ----------- WebElemet definition: ---------------
	
	@FindBy(xpath="//input[@type='email' and contains(@data-qa,'login-email') and contains(@name,'email')]")
	WebElement loginemailInput;
	
	@FindBy(xpath="//input[@type='password' and contains(@data-qa,'login-password') and contains(@name,'password')]")
	WebElement loginpasswordInput;
	
	@FindBy(xpath="//button[@type='submit' and text()='Login']")
	WebElement loginBtn;
	
	String erroMsgLocator = "//p[normalize-space()='%s']";
	
	// New user
	@FindBy(xpath="//input[@type='text' and contains(@data-qa,'signup-name') and contains(@name,'name')]")
	WebElement newUserName;
		
	@FindBy(xpath="//input[@type='email' and contains(@data-qa,'signup-email') and contains(@name,'email')]")
	WebElement newUserEmailAdds;
		
	@FindBy(xpath="//button[@type='submit' and text()='Signup']")
	WebElement signupBtn;
	

	public LoginPage(WebDriver driver, String url) {
		super(driver, url);
	}
	
	public InputElement loginemailInput() {
		return new InputElement(driver, loginemailInput, "Email Input");
	}

	public InputElement loginpasswordInput() {
		return new InputElement(driver, loginpasswordInput, "Password Input");
	}
	
	public Button loginBtn() {
		return new Button(driver, loginBtn, "log in button");
	}
	
	public boolean errorMsg(String expectedErroMsg) {
		TextElement erroText = new TextElement(driver, null, "Error message");
		return erroText.dinamicText(erroMsgLocator, expectedErroMsg);
	}
	
	public InputElement newUserName() {
		return new InputElement(driver, newUserName, "New user name Input");
	}
	
	public InputElement newUserEmailAdds() {
		return new InputElement(driver, newUserEmailAdds, "New email address Input");
	}
	
	public Button signupBtn() {
		return new Button(driver, signupBtn, "Sigup Button");
	}

}
