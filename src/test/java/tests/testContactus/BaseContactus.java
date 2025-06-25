package tests.testContactus;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import pages.ContactusPage;
import pages.MainPage;
import tests.BaseTest;
import ui.InputElement;


public class BaseContactus extends BaseTest { 
	MainPage mainpage;
	ContactusPage contactpage;
	
	@Parameters({"url", "contactus"})
	@BeforeMethod
	void setup(String url, String contactus) throws Exception {
		this.driver = new ChromeDriver();
		this.mainpage = new MainPage(this.driver, url);
		this.contactpage =  new ContactusPage(this.driver, url + contactus);
		boolean isLaunched = super.setup(contactpage);
		if (!isLaunched) {
			throw new Exception("The wen page could not be launched");
		}
	}
	
	@AfterMethod
	public boolean teardown() {
		boolean isClosed = super.teardown();
		Assert.assertEquals(isClosed, true);
		return isClosed;

	}
	
	public void checkFormatfilledCorreclty(String name, String email, String subject, String message, String filepath, String expectedSuccessMsg) {
		String currentContext = getNeededHdl(0);
		stepMsg("Check the format information has been fill correclty");
		StepCheckFileUploaded(this.contactpage.choofileUpload(), filepath);
		 List<Map<InputElement, String>> inputMapList = new ArrayList<>();
		    inputMapList.add(Map.of(this.contactpage.nameInput(), name));
		    inputMapList.add(Map.of(this.contactpage.emailInput(), email));
		    inputMapList.add(Map.of(this.contactpage.subjectInput(), subject));
		    inputMapList.add(Map.of(this.contactpage.messageInput(), message));
		verifyFillAndClick(inputMapList, this.contactpage.submitBtn(), false);
		awaiting(1);
		this.driver.switchTo().alert().accept();
		awaiting(1);
		this.driver.switchTo().window(currentContext);
		checkTextElemtValue(this.contactpage.scuccessText(), expectedSuccessMsg);
		
	}

}
