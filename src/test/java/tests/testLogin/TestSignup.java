package tests.testLogin;
import java.util.List;
import java.util.Map;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class TestSignup extends BaseTestLogin {
	
	@Parameters("signupurl")
	@Test(groups = {"signup", "regression"})
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
		stepVerifyPageOpenAfterClickBtn(this.accpage.continueBtn());
		// Verify that 'Logged in as username' is visible
		stepCheckLogged(username);
		// Click 'Delete Account' button
		stepVerifyPageOpenAfterClickBtn(this.mainpage.deleteAccBtn());
		// Verify that 'ACCOUNT DELETED!' is visible and click 'Continue' button
		stepVerifyDeleteAccount(expectedAccDeltitle);
	}
	
	
	@Parameters({"userExisted", "emailExisted", "expectedErrorMsg"})
	@Test(groups = {"signup", "sanity"})
	void testRegisterExistingUser(String userExisted, String emailExisted, String expectedErrorMsg) {
		stepVerifysignup(userExisted, emailExisted, expectedErrorMsg, true);
	}
	

}
