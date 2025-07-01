package tests.testProducts;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class TestSearchProduct extends BaseTestProduct{
	
	
	@Test(dataProvider="productSearch", groups = {"products", "search product", "regression" }, priority = 2)
	void testSearchProduct(Object productSearch, boolean findProduct) {
		checkTextElemtValue(this.productpage.title(), this.expectedTiltle);
		StepSearchProduct(productSearch.toString(), findProduct);
	}
	
	
	@DataProvider(name="productSearch")
	public Object[][] data(){
		return new Object[][] {
			{"Top", true},
			{"Blue", true},
			{"winter", true},
			{"mariposas", false},
		};
	}
	
	
}
