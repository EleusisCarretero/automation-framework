package tests;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import com.google.common.base.Supplier;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.opentelemetry.internal.shaded.jctools.queues.MessagePassingQueue.Consumer;
import pages.LoginPage;
import pages.SigupPage;

public class TestLogin extends BaseTest {
	LoginPage page;
	SigupPage siguppage;
	
	@Parameters("url")
	@BeforeMethod
	void setup(String url) throws Exception {
		this.driver = new ChromeDriver();
		this.page = new LoginPage(this.driver, url);
		this.siguppage = new SigupPage(this.driver, "https://automationexercise.com/signup");
		boolean isLaunched = super.setup(page);
		if (!isLaunched) {
			throw new Exception("The wen page could not be launched");
		}
	}
	
	@Parameters("signupurl")
	@Test
	void testNewUSer(String signupurl) throws InterruptedException {
		String name = "Pedrio";
		String email ="pascal@htm.com";
		String birthday = "20-April-2020";
		
		// 1 Write credentials
		verifyDatawritten(name, page::newUserName, page::newUserName);
		verifyDatawritten(email, page::newUserEmailAdds,page::newUserEmailAdds);
		Thread.sleep(Duration.ofSeconds(2));
		// 2 signup
		page.signupBtn();
		// 3 verify url
		Thread.sleep(Duration.ofSeconds(1));
		String currentURl = switchContext(0);
		Assert.assertEquals(currentURl, signupurl);
		
		// 4 set birthday
		String[] birthdayFields = birthday.split("-");
		
		Map<Supplier<String>, Consumer<String>> acc = Map.of(
			    this.siguppage::ddDays, this.siguppage::ddDays,
			    this.siguppage::ddMonths, this.siguppage::ddMonths,
			    this.siguppage::ddYears, this.siguppage::ddYears
			);
		int bpos = 0;
		for(Map.Entry<Supplier<String>, Consumer<String>> entry: acc.entrySet()) {
			 Consumer<String> writer =  entry.getValue();
			 Supplier<String> reader = entry.getKey();
			 verifyDropDown(birthdayFields[bpos],  writer, reader);
			 Thread.sleep(Duration.ofSeconds(1));
			 bpos ++;
		}
		
		
		
	}
	
	
	@AfterMethod
	boolean teardown() {
		boolean isClosed = super.teardown();
		Assert.assertEquals(isClosed, true);
		return isClosed;

	}

}
