package tests.testProducts;

import java.util.List;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import pages.AccountCreatedPage;
import pages.DeleteAccountPage;
import pages.LoginPage;
import pages.MainPage;
import pages.ProductsPage;
import pages.SigupPage;
import tests.BaseTest;
import ui.PreliminarProductView;

public class BaseTestProduct extends BaseTest {
	MainPage mainpage;
	ProductsPage productpage;
	
	@Parameters({"url", "productsurl"})
	@BeforeMethod
	void setup(String url, String productsurl) throws Exception {
		this.driver = new ChromeDriver();
		this.mainpage = new MainPage(this.driver, url);
		this.productpage = new ProductsPage(this.driver, url + productsurl);
		boolean isLaunched = super.setup(productpage);
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
	
	public void stepCheckElementsVisible() {
		stepMsg("Check all producs are visible");
		List<PreliminarProductView> products = this.productpage.products();
		boolean caliz = products.get(0).isVisible();
		System.out.println("Here: " + caliz);
		//boolean allVisible = products.stream()
		//		.allMatch(p -> p.isVisible());
		//this.assertManager.checkIsTrue(allVisible);
	}


}
