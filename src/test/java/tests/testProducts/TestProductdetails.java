package tests.testProducts;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import ui.TextElement;

public class TestProductdetails extends BaseTestProduct{
	
	
	@Parameters("expectedTiltle")
	@Test
	void testAllProductsAndProductDetail(String expectedTiltle) {
		checkTextElemtValue(this.productpage.title(), expectedTiltle);
		stepCheckElementsVisible();
	}

}
