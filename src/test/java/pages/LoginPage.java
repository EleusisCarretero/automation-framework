package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {
	
	//WebElemet definition:
	
	// Login elemets
	@FindBy(xpath="//input[@type='email' and contains(@data-qa,'login-email') and contains(@name,'email')]")
	WebElement loginEmailAdds;
	
	@FindBy(xpath="//input[@type='password' and contains(@data-qa,'login-password') and contains(@name,'password')]")
	WebElement loginPsswAdds;
	
	@FindBy(xpath="//button[@type='submit' and text()='Login']")
	WebElement loginBtn;
	
	// New user
	@FindBy(xpath="//input[@type='text' and contains(@data-qa,'signup-name') and contains(@name,'name')]")
	WebElement newUserName;
	
	@FindBy(xpath="//input[@type='email' and contains(@data-qa,'signup-email') and contains(@name,'email')]")
	WebElement newUserEmailAdds;
	
	@FindBy(xpath="//button[@type='submit' and text()='Signup']")
	WebElement signupBtn;

	public LoginPage(WebDriver driver, String url) {
		super(driver, url);
		// TODO Auto-generated constructor stub
	}
	
	public void loginEmailAdds(String email) {
		writeElement(loginEmailAdds, email);
	}
	
	public void loginPsswAdds(String password) {
		writeElement(loginPsswAdds, password);
	}
	
	public void loginBtn() {
		clickElemet(loginBtn);
	}
	
	public void newUserName(String name) {
		writeElement(newUserName, name);
	}
	public String newUserName() {
		return readTextElement(newUserName);
	}
	
	public void newUserEmailAdds(String email) {
		writeElement(newUserEmailAdds, email);
	}
	
	public String newUserEmailAdds() {
		return readTextElement(newUserEmailAdds);
	}
	
	public void signupBtn() {
		clickElemet(signupBtn);
	}


}
