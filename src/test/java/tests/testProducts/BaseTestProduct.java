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
import pages.ProductDetailPage;
import pages.ProductsPage;
import pages.SigupPage;
import tests.BaseTest;
import ui.PreliminarProductView;

public class BaseTestProduct extends BaseTest {
	MainPage mainpage;
	ProductsPage productpage;
	ProductDetailPage productdetailpage;
	
	@Parameters({"url", "productsurl", "singleproducturl"})
	@BeforeMethod
	void setup(String url, String productsurl, String singleproducturl) throws Exception {
		this.driver = new ChromeDriver();
		this.mainpage = new MainPage(this.driver, url);
		this.productpage = new ProductsPage(this.driver, url + productsurl);
		this.productdetailpage = new ProductDetailPage(this.driver, url + singleproducturl);
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
	
	public void stepCheckElementsVisible(boolean closeAdvert) {
		if (closeAdvert) {
			LOGGER.info("Closing advertising by clicking on 900, 850");
			this.productdetailpage.clickOnCoordinates(840, 860);
		}
		stepMsg("Check all producs are visible");
		List<PreliminarProductView> products = this.productpage.products();
		boolean allVisible = products.stream()
				.allMatch(p -> p.isVisible());
		this.assertManager.checkIsTrue(allVisible);
	}
	
	public void stepCheckDetailsPageProduct(int indexProduct) {
		stepMsg("Check the select product page datils has opened");
		// 1. select product by index
		PreliminarProductView productSelected = this.productpage.getSingleProduct(indexProduct);
		// 2. click on view product
		stepVerifyPageOpenAfterClickBtn(productSelected.viewProductBtn());
		// 3. Check url
		int producturl = indexProduct + 1;
		checkUrl(this.productdetailpage.url() + producturl, true);
	}

	public void stepCheckDetailsProductVisible() {
		// check detais visible
		stepMsg("Check all product page details are visible");
		boolean detailsVisible = this.productdetailpage.productcarddetails().isVisible();
		this.assertManager.checkIsTrue(detailsVisible);
	}
	

}
