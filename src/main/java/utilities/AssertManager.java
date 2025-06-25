package utilities;
import java.util.logging.Logger;
import org.testng.Assert;


public class AssertManager {
	protected static final Logger LOGGER = Logger.getLogger(AssertManager.class.getName());
	private static String errorMessage = "FAILED - The current assertion has failed | ";
	private static String throwErrorMessage = "FAILED - The actual execution has raised an exception | ";
	
	public void checkEqualsTo(Object actual, Object expected) {
		Assert.assertEquals(actual, expected, errorMessage);
	}
	public void CheckNotEqualsTo(Object actual, Object expected) {
		Assert.assertNotEquals(actual, expected, errorMessage);
	}
	
	public void checkIsTrue(boolean status) {
		Assert.assertTrue(status, errorMessage);
	}
	
	public void checkIsFalse(boolean status) {
		Assert.assertFalse(status, errorMessage);
	}
	
	public void checkAnyThrowsRaised(Runnable runnable) {
		try {
	        runnable.run();
	    } catch (Exception e) {
	        Assert.fail(throwErrorMessage + e.getClass().getSimpleName() + " → " + e.getMessage());
	    }
	}

	public void assertNotThrowsOfType(Runnable runnable, String erroMessage, @SuppressWarnings("unchecked") Class<? extends Throwable>... excepcionesCriticas) {
	    try {
	        runnable.run();
	    } catch (Throwable e) {
	        for (Class<? extends Throwable> exTipo : excepcionesCriticas) {
	            if (exTipo.isInstance(e)) {
	                Assert.fail(throwErrorMessage + erroMessage + e.getClass().getSimpleName() + " → " + e.getMessage());
	            }
	        }
	        LOGGER.severe("Other excpetion has been raised " + e.getClass().getSimpleName());
	    }
	}



}
