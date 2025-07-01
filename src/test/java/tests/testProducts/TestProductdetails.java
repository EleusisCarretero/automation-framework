package tests.testProducts;
import org.testng.annotations.Parameters;


public class TestProductdetails extends BaseTestProduct{
	
	
	@Parameters("expectedTiltle")
	//@Test
	void testAllProductsAndProductDetail(String expectedTiltle) {
		checkTextElemtValue(this.productpage.title(), expectedTiltle);
		stepCheckElementsVisible(true,true);
		stepCheckDetailsPageProduct(0);
		stepCheckDetailsProductVisible();
		
	}

}
