package tests.testProducts;
import java.util.List;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import io.qameta.allure.Step;
import pages.MainPage;
import pages.ProductDetailPage;
import pages.ProductsPage;
import tests.BaseTest;
import ui.PreliminarProductView;


public class BaseTestProduct extends BaseTest {
	MainPage mainpage;
	ProductsPage productpage;
	ProductDetailPage productdetailpage;
	
	String expectedTiltle;
	
	@BeforeClass(alwaysRun = true)
	@Parameters("expectedTiltle")
	public void setExpectedTitle(String expectedTiltle) {
		this.expectedTiltle = expectedTiltle;
	}
	
	@Parameters({"browser", "args", "url", "productsurl", "singleproducturl"})
	@BeforeMethod(alwaysRun = true)
	void setup(String browser, String args, String url, String productsurl, String singleproducturl) throws Exception {
		initBrowser(browser, args);
		this.mainpage = new MainPage(this.driver, url);
		this.productpage = new ProductsPage(this.driver, url + productsurl);
		this.productdetailpage = new ProductDetailPage(this.driver, url + singleproducturl);
		boolean isLaunched = super.setup(productpage);
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
	
	@Step("Check all products are visible")
	public void stepCheckElementsVisible(boolean visible, boolean closeAdvert) {
		if (closeAdvert) {
			checkCloseAvdertising( this.productpage, 830, 860);
		}
		stepMsg("Check all producs are visible");
		List<PreliminarProductView> products = this.productpage.products();
		boolean allVisible = !products.isEmpty() && products.stream().allMatch(p -> p.isVisible());
		if(visible) {
			this.assertManager.checkIsTrue(allVisible);
		}
		else {
			this.assertManager.checkIsFalse(allVisible);
		}
	}
	
	@Step("Check the select product index {indexProduct} page datils has opened")
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

	@Step("Check all product page details are visible")
	public void stepCheckDetailsProductVisible() {
		// check detais visible
		stepMsg("Check all product page details are visible");
		boolean detailsVisible = this.productdetailpage.productcarddetails().isVisible();
		this.assertManager.checkIsTrue(detailsVisible);
	}
	
	@Step("Searching product {searchProduct}")
	public void StepSearchProduct(String searchProduct, boolean findProduct) {
		// 1. Enter product to looking for and click on
		stepMsg("Searching product " + searchProduct);
		boolean searchStatus = this.productdetailpage.productSearchBar().search(searchProduct, true, false);
		this.assertManager.checkIsTrue(searchStatus);
		// 2. check title
		checkTextElemtValue(this.productdetailpage.searchProductTitle(), "SEARCHED PRODUCTS");
		// 3. check products visible
		stepCheckElementsVisible(findProduct, true);
	}
	

}
