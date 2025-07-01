package tests.testLogin;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import pages.AccountCreatedPage;
import pages.DeleteAccountPage;
import pages.LoginPage;
import pages.MainPage;
import pages.SigupPage;
import tests.BaseTest;
import ui.DropDownElement;
import ui.InputElement;


public class BaseTestLogin extends BaseTest {
	LoginPage loginpage;
	SigupPage siguppage;
	AccountCreatedPage accpage;
	MainPage mainpage;
	DeleteAccountPage deletaepage;
	
	@BeforeMethod(alwaysRun = true)
	@Parameters({"url", "loginurl", "signupurl", "accurl", "acdurl"})
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
	
	@AfterMethod(alwaysRun = true)
	public boolean teardown() {
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
	
	void stepVerifylogin(String email, String password) {
		stepMsg("Verify that the login has been succesfull");
		List<Map<InputElement, String>> inputMapList = buildInputMap(
			    new InputElement[] { this.loginpage.loginemailInput(), this.loginpage.loginpasswordInput() },
			    new String[] { email, password }
			);
		verifyFillAndClick(inputMapList, this.loginpage.loginBtn(), true);
		checkUrl(this.mainpage.url(), true);
	}
	
	void stepVerifylogin(String email, String password, String exptectedErroMsg) {
		stepMsg("Verify that the login has fails and error message " +  exptectedErroMsg + " is displayed");
		List<Map<InputElement, String>> inputMapList = buildInputMap(
			    new InputElement[] { this.loginpage.loginemailInput(), this.loginpage.loginpasswordInput() },
			    new String[] { email, password }
			);
		verifyFillAndClick(inputMapList, this.loginpage.loginBtn(), false);
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
		verifyFillAndClick(inputMapList, this.loginpage.signupBtn(), true);
		awaiting(1);
		// 3. check url
		checkUrl(this.loginpage.url(), false);
		// 4 check title page
		checkTextElemtValue(this.siguppage.titleIntoText(), expecteTitle);
	}
	
	void stepVerifysignup(String name, String email, String exptectedErroMsg, boolean s) {
		// 1. Enter credentials
		stepMsg("Verify the signup is not allowed and erro message is displayed");
		List<Map<InputElement, String>> inputMapList = buildInputMap(
			    new InputElement[] { this.loginpage.newUserName(), this.loginpage.newUserEmailAdds() },
			    new String[] { name, email }
			);
		// 2. Click singup button
		verifyFillAndClick(inputMapList, this.loginpage.signupBtn(), true);
		awaiting(1);
		// 3. check url
		checkUrl(this.loginpage.url(), true);
		// 4 check error
		LOGGER.info("Check the actual error message has the expected value " + exptectedErroMsg);
		boolean hasText = this.loginpage.errorMsg(exptectedErroMsg);
		this.assertManager.checkIsTrue(hasText);
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
	    verifyFillAndClick(inputMapList, this.siguppage.createAccountBtn(), true);
	    //5. Check title and created user page opened
	    checkTextElemtValue(this.accpage.titleIntoText(), expectedAccTitle);
	}

	void stepCheckLogged(String name) {
		stepMsg("Check the Logged name matches with the expetced " + name);
	    checkTextElemtValue(this.mainpage.loggedTextElement(), "Logged in as " + name);
	}
	
	void stepVerifyDeleteAccount(String expectedAccDeltitle) {
	    checkTextElemtValue(this.deletaepage.deletedTitle(), expectedAccDeltitle);
	    stepVerifyPageOpenAfterClickBtn(this.deletaepage.continueBtn());
	}
	
	void stepCheckLogout() {
		stepMsg("Check the user has logout succesfully");
		verifySimpleClick(this.mainpage.logoutBtn());
		String expectedUrl = this.loginpage.url();
		String actualurl = switchContext(0);
		this.assertManager.checkEqualsTo(actualurl, expectedUrl);
	}

}
