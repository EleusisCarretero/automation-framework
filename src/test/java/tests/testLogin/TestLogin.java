package tests.testLogin;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class TestLogin extends BaseTestLogin {
	
	
	@Parameters({"email", "password", "user"})
	@Test
	void testLogoutUSer(String email, String password, String user) {
		stepVerifylogin(email, password);
		stepCheckLogged(user);
		stepVerifyPageOpenAfterClickBtn(this.mainpage.logoutBtn());
		checkUrl(this.loginpage.url(), true);
		stepCheckLogout();
	}

	@Test
	void testLoginInvalidUser() throws InterruptedException {
		String email = "Pedrio@mail.com";
		String password ="123unodos";
		String errorMsg = "Your email or password is incorrect!";
		stepVerifylogin(email, password, errorMsg);
	}

}
