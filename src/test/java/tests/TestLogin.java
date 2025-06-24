package tests;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import pages.AccountCreatedPage;
import pages.DeleteAccountPage;
import pages.LoginPage;
import pages.MainPage;
import pages.SigupPage;
import ui.DropDownElement;
import ui.InputElement;


public class TestLogin extends BaseTest {
	LoginPage loginpage;
	SigupPage siguppage;
	AccountCreatedPage accpage;
	MainPage mainpage;
	DeleteAccountPage deletaepage;
	
	@Parameters({"url", "loginurl", "signupurl", "accurl", "acdurl"})
	@BeforeMethod
	void setup(String url, String loginurl, String signupurl, String accurl, String acdurl) throws Exception {
		this.driver = new ChromeDriver();
		this.loginpage = new LoginPage(this.driver, url + loginurl);
		this.siguppage = new SigupPage(this.driver, url + signupurl);
		this.accpage = new AccountCreatedPage(this.driver,url + accurl);
		this.mainpage = new MainPage(this.driver, url);
		this.deletaepage = new DeleteAccountPage(this.driver, url + acdurl);
		boolean isLaunched = super.setup(loginpage);
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
			    new InputElement[] { this.loginpage.loginemailInput(), this.loginpage.loginpasswordInput() },
			    new String[] { email, password }
			);
		verifyFillAndClick(inputMapList, this.loginpage.loginBtn());
	}
	
	void verifylogin(String email, String password, String exptectedErroMsg) {
		List<Map<InputElement, String>> inputMapList = buildInputMap(
			    new InputElement[] { this.loginpage.loginemailInput(), this.loginpage.loginpasswordInput() },
			    new String[] { email, password }
			);
		verifyFillAndClick(inputMapList, this.loginpage.loginBtn());
		// verify Error msg
		LOGGER.info("Check the actual error message has the expected value " + exptectedErroMsg);
		boolean hasText = this.loginpage.errorMsg(exptectedErroMsg);
		this.assertManager.checkIsTrue(hasText);
	}
	
	void stepVerifysignup(String name, String email, String expecteTitle) {
		// 1. Enter credentials
		stepMsg("Verify the sigup fields has fill successfully and reaching siguo url");
		List<Map<InputElement, String>> inputMapList = buildInputMap(
			    new InputElement[] { this.loginpage.newUserName(), this.loginpage.newUserEmailAdds() },
			    new String[] { name, email }
			);
		// 2. Click singup button
		verifyFillAndClick(inputMapList, this.loginpage.signupBtn());
		awaiting(1);
		// 3. check url
		checkUrl(this.loginpage.url(), false);
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
	    // 3. 1 close advert
	    this.loginpage.clickOnCoordinates(advertCoors.get(0), advertCoors.get(1));
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
		// Read a random user from api
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
