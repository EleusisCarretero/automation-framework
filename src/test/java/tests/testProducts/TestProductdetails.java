package tests.testProducts;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


public class TestProductdetails extends BaseTestProduct{
	
	
	@Parameters("expectedTiltle")
	@Test(groups = {"prducts","product details", "smoke"})
	void testAllProductsAndProductDetail(String expectedTiltle) {
		checkTextElemtValue(this.productpage.title(), expectedTiltle);
		stepCheckElementsVisible(true,true);
		stepCheckDetailsPageProduct(0);
		stepCheckDetailsProductVisible();
		
	}

}
