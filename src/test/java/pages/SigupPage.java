package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utilities.CheckboxButton;
import utilities.DropDownElement;
import utilities.InputElement;
import utilities.RadioButton;
import utilities.TextElement;
import utilities.Button;

public class SigupPage extends BasePage {
	
	// ENTER ACCOUNT INFORMATION LOCATOR
	
	@FindBy(xpath="//h2[contains(@class, 'title')][1]")
	WebElement titleIntoText;
	
	@FindBy(xpath="//input[@type='radio' and @value='Mr']")
	WebElement mrRadioBtn;
	
	@FindBy(xpath="//input[@type='radio' and @value='Mrs']")
	WebElement mrsRadioBtn;
	
	@FindBy(xpath="//input[@type='text' and @id='name']")
	WebElement nameInput;
	
	@FindBy(xpath="//input[@type='text' and @id='email']")
	WebElement emailInput;
	
	@FindBy(xpath="//input[@type='password' and @id='password']")
	WebElement passwordInput;
	
	// data birthday
	@FindBy (id="days")
	WebElement daysDropDown;
	
	@FindBy (id="months")
	WebElement monthsDropDown;
	
	@FindBy (id="years")
	WebElement yearsDropDown;
	
	// Check boxes
	@FindBy (xpath="//input[@type='checkbox' and @id='newsletter']")
	WebElement sigupCheckboxBtn;
	
	@FindBy (xpath="//input[@type='checkbox' and @id='optin']")
	WebElement receiveCheckboxBtn;
	
	// ADRESS INFORNMATION
	@FindBy (id="first_name")
	WebElement firstnameInput;
	
	@FindBy (id="last_name")
	WebElement lastnameInput;
	
	@FindBy (id="company")
	WebElement companyInput;
	
	@FindBy (id="address1")
	WebElement address1Input;
	
	@FindBy (id="address2")
	WebElement address2Input;
	
	@FindBy (css="select[id='country'][name='country']")
	WebElement countryDropDown;
	
	@FindBy(css="input[type='text'][id='state']")
	WebElement stateInput;
	
	@FindBy(css="input[type='text'][id='city']")
	WebElement cityInput;
	
	@FindBy(css="input[type='text'][id='zipcode']")
	WebElement zipcodeInput;
	
	@FindBy(css="input[type='text'][id='mobile_number']")
	WebElement mobileNumberInput;
	
	@FindBy(xpath="//button[text()='Create Account']")
	WebElement createAccountBtn;

	
	public SigupPage(WebDriver driver, String url) {
		super(driver, url);
	}

	public TextElement titleIntoText() {
		return new TextElement(driver, titleIntoText, "Enter Title");
	}
	
	public RadioButton mrRadioBtn() {
		return new RadioButton(driver, mrRadioBtn, "Mr Radio button");
	}
	
	public RadioButton mrsRadioBtn() {
		return new RadioButton(driver, mrsRadioBtn, "Mrs Radio button");
	}
	
	public DropDownElement daysDropDown() {
		return new DropDownElement(driver, daysDropDown, "Dropdown days");
	}
	
	public DropDownElement monthsDropDown()  {
		return new DropDownElement(driver, monthsDropDown, "Dropdown months");
	}
	
	public DropDownElement yearsDropDown() {
		return new DropDownElement(driver, yearsDropDown, "Dropdown year");
	}
	
	public InputElement nameInput() {
		return new InputElement(driver, nameInput, "Name input");
	}
	
	public InputElement emailInput() {
		return new InputElement(driver, emailInput, "Email input");
	}
	
	public InputElement passwordInput() {
		return new InputElement(driver, passwordInput, "Password input");
	}
	
	public CheckboxButton sigupCheckboxBtn() {
		return new CheckboxButton(driver, sigupCheckboxBtn, "Sig up for newsletter checkbox");
	}
	
	public CheckboxButton receiveCheckboxBtn() {
		return new CheckboxButton(driver, receiveCheckboxBtn, "Receive special offers checkbox");
	}
	
	public InputElement firstnameInput() {
		return new InputElement(driver, firstnameInput, "First name input");
	}
	
	public InputElement lastnameInput() {
		return new InputElement(driver, lastnameInput, "Last name input");
	}
	
	public InputElement companyInput() {
		return new InputElement(driver, companyInput, "Company input");
	}
	
	public InputElement address1Input() {
		return new InputElement(driver, address1Input, "Address1 input");
	}
	
	public InputElement address2Input() {
		return new InputElement(driver, address2Input, "Addres2 input");
	}
	
	public DropDownElement countryDropDown()  {
		return new DropDownElement(driver, countryDropDown, "Country dropdown");
	}
	
	public InputElement stateInput() {
		return new InputElement(driver, stateInput, "State input");
	}
	public InputElement cityInput() {
		return new InputElement(driver, cityInput, "City input");
	}
	public InputElement zipcodeInput() {
		return new InputElement(driver, zipcodeInput, "Addres2 input");
	}
	public InputElement mobileNumberInput() {
		return new InputElement(driver, mobileNumberInput, "Mobile Number input");
	}
	public Button createAccountBtn() {
		return new Button(driver, createAccountBtn, "Create account Button");
	}
	

}
