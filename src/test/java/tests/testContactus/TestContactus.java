package tests.testContactus;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class TestContactus extends BaseContactus {
	
	@Parameters({"name","email","subject","message", "filepath", "expectedSuccessMsg"})
	@Test
	void testFillandSendFormat(String name, String email, String subject, String message, String filepath, String expectedSuccessMsg) {
		// 1. Chose file
		checkFormatfilledCorreclty(name, email, subject, message, filepath, expectedSuccessMsg);
	}

}
