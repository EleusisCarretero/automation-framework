package tests;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.google.common.base.Supplier;

import org.openqa.selenium.Alert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.opentelemetry.internal.shaded.jctools.queues.MessagePassingQueue.Consumer;
import pages.AccountCreatedPage;
import pages.DeleteAccountPage;
import pages.LoginPage;
import pages.MainPage;
import pages.SigupPage;
import utilities.DropDownElement;
import utilities.InputElement;

public class TestLogin extends BaseTest {
	LoginPage page;
	SigupPage siguppage;
	AccountCreatedPage accpage;
	MainPage mainpage;
	DeleteAccountPage deletaepage;
	
	@Parameters("url")
	@BeforeMethod
	void setup(String url) throws Exception {
		this.driver = new ChromeDriver();
		this.page = new LoginPage(this.driver, url);
		this.siguppage = new SigupPage(this.driver, "https://automationexercise.com/signup");
		this.accpage = new AccountCreatedPage(this.driver,"https://automationexercise.com/account_created");
		this.mainpage = new MainPage(this.driver, "https://automationexercise.com/");
		this.deletaepage = new DeleteAccountPage(this.driver, "https://automationexercise.com/delete_account");
		boolean isLaunched = super.setup(page);
		if (!isLaunched) {
			throw new Exception("The wen page could not be launched");
		}
	}
	
	@AfterMethod
	boolean teardown() {
		boolean isClosed = super.teardown();
		Assert.assertEquals(isClosed, true);
		return isClosed;

	}
	
	List<Map<InputElement, String>> buildInputMap(InputElement[] elements, String[] values) {
	    List<Map<InputElement, String>> list = new ArrayList<>();
	    for (int i = 0; i < elements.length; i++) {
	        list.add(Map.of(elements[i], values[i]));
	    }
	    return list;
	}

	
	void verifylogin(String email, String password) {
		List<Map<InputElement, String>> inputMapList = buildInputMap(
			    new InputElement[] { this.page.loginemailInput(), this.page.loginpasswordInput() },
			    new String[] { email, password }
			);
		verifyFillAndClick(inputMapList, this.page.loginBtn());
	}
	
	void verifylogin(String email, String password, String exptectedErroMsg) {
		List<Map<InputElement, String>> inputMapList = buildInputMap(
			    new InputElement[] { this.page.loginemailInput(), this.page.loginpasswordInput() },
			    new String[] { email, password }
			);
		verifyFillAndClick(inputMapList, this.page.loginBtn());
		// verify Error msg
		String actualErrorMsg = this.page.errorMsg();
		LOGGER.info("Check the actual error message " + actualErrorMsg + " has the expected value " + exptectedErroMsg);
		Assert.assertEquals(this.page.errorMsg(), exptectedErroMsg);
	}
	
	void stepVerifysignup(String name, String email, String expecteTitle) {
		// 1. Enter credentials
		stepMsg("Verify the sigup fields has fill successfully and reaching siguo url");
		List<Map<InputElement, String>> inputMapList = buildInputMap(
			    new InputElement[] { this.page.newUserName(), this.page.newUserEmailAdds() },
			    new String[] { email, email }
			);
		// 2. Click singup button
		verifyFillAndClick(inputMapList, this.page.signupBtn());
		awaiting(1);
		// 3. check url
		checkUrl(this.page.url(), false);
		// 4 check title page
		checkTextElemtValue(this.siguppage.titleIntoText(), expecteTitle);
	}
	
	void stepVerifyfillBirthday(String birthday) {
		List<String> birthdayFields =  new ArrayList<>();
		stepMsg("Verify Dropdown birthay have been set successfully");
		for(String s: birthday.split("-")) {
			birthdayFields.add(Integer.toString(Integer.parseInt(s)));
		}
	    List<Boolean> visibleFlags = List.of(true, false, true);
	    List<DropDownElement> dropDownList = List.of(
	    	this.siguppage.yearsDropDown(),
	    	this.siguppage.monthsDropDown(),
	        this.siguppage.daysDropDown()
	    );
	    verifyMultipleDropDownSet(dropDownList, birthdayFields, visibleFlags);
	}

	void stepVerifySignupData(String username, String email, String password, String nametitle, String birthday, String name,
            String lastname, String company, String address1, String address2,
            String state, String city, String zipcode, String mobileNum, String expectedAccTitle) {

		stepMsg("Verifu all new user data information is correclty filled and the new user page opens");
		// 1. User account information
		checkInputFill(this.siguppage.nameInput(), username);
	    checkInputFill(this.siguppage.emailInput(), email);
	    verifyInputFill(this.siguppage.passwordInput(), password, true);
	    if ("Mr".equals(nametitle)) {
	    	verifyRadioBtnChecked(this.siguppage.mrRadioBtn());
	    }
	    else {
	    	verifyRadioBtnChecked(this.siguppage.mrsRadioBtn());
	    }
	    verifyCheckboxButton(this.siguppage.sigupCheckboxBtn(), true);
	    verifyCheckboxButton(this.siguppage.receiveCheckboxBtn(), true);
	    // 2. Fill birthday information
	    stepVerifyfillBirthday(birthday);
	    // 3. Select country
	    verifyDropDownSet(this.siguppage.countryDropDown(), "Australia", false);
	    // 4. Fill user personal data user info
	    List<Map<InputElement, String>> inputMapList = new ArrayList<>();
	    inputMapList.add(Map.of(this.siguppage.firstnameInput(), name));
	    inputMapList.add(Map.of(this.siguppage.lastnameInput(), lastname));
	    inputMapList.add(Map.of(this.siguppage.companyInput(), company));
	    inputMapList.add(Map.of(this.siguppage.address1Input(), address1));
	    inputMapList.add(Map.of(this.siguppage.address2Input(), address2));
	    inputMapList.add(Map.of(this.siguppage.stateInput(), state));
	    inputMapList.add(Map.of(this.siguppage.cityInput(), city));
	    inputMapList.add(Map.of(this.siguppage.zipcodeInput(), zipcode));
	    inputMapList.add(Map.of(this.siguppage.mobileNumberInput(), mobileNum));
	    verifyFillAndClick(inputMapList, this.siguppage.createAccountBtn());
	    //5. Check title and created user page opened
	    checkTextElemtValue(this.accpage.titleIntoText(), expectedAccTitle);
	}

	void stepCheckLogged(String name) {
		stepMsg("Check the Logged name matches with the expetced " + name);
	    checkTextElemtValue(this.mainpage.loggedTextElement(), "Logged in as " + name);
	}
	
	void stepVerifyDeleteAccount(String expectedAccDeltitle) {
	    checkTextElemtValue(this.deletaepage.deletedTitle(), expectedAccDeltitle);
	    stepVerifyPageOpenAfterClickBtn(this.deletaepage.continueBtn(), this.mainpage);
	}


	
	@Parameters("signupurl")
	@Test
	void testNewUSer(String signupurl) throws InterruptedException {
		Map<String, Object> values = getUserData(List.of(
				"login.username",
				"email",
				"login.password",
				"dob.date",
				"name.first",
				"name.last",
				"name.title",
				"location.postcode",
				"phone",
				"location.city",
				"location.street.number",
				"location.street.name"
			)
		);
		String username = (String) values.get("login.username");
		String email = (String) values.get("email");
		String password = (String) values.get("login.password");
		String birthday = ((String) values.get("dob.date")).split("T")[0];
		String expecteTitle = "ENTER ACCOUNT INFORMATION";
		String name = (String) values.get("name.first");
		String lastname = (String) values.get("name.last");
		String nametitle = (String) values.get("name.title");
		String company = "Apple";
		String address1 = (String) values.get("location.street.name") + "#" + (String) values.get("location.street.number");
		String address2 = (String) values.get("location.street.name") + "#" + (String) values.get("location.street.number");
		String state = "Jalisco";
		String city = (String) values.get("location.city");
		String zipcode = (String) values.get("location.postcode");
		String mobileNum = (String) values.get("phone");
		String expectedAccTitle = "ACCOUNT CREATED!";
		String expectedAccDeltitle = "ACCOUNT DELETED!";
		// Fill singup
		stepVerifysignup(username, email, expecteTitle);
		// new user data
		stepVerifySignupData(username, email, password, nametitle, birthday, name, lastname, company, address1, address2, state, city, zipcode, mobileNum, expectedAccTitle);
		// Click 'Continue' button
		stepVerifyPageOpenAfterClickBtn(this.accpage.continueBtn(), this.accpage);
		// Verify that 'Logged in as username' is visible
		stepCheckLogged(username);
		// Click 'Delete Account' button
		stepVerifyPageOpenAfterClickBtn(this.mainpage.deleteAccBtn(), this.deletaepage);
		// Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
		stepVerifyDeleteAccount(expectedAccDeltitle);
	}

	@Test
	void testLoginInvalidUser() throws InterruptedException {
		String email = "Pedrio@mail.com";
		String password ="123unodos";
		String errorMsg = "Your email or password is incorrect!";
		verifylogin(email, password, errorMsg);
	}
	

}
