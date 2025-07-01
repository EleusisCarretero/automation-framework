package ui;
import java.io.File;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Upload extends Element {

	public Upload(WebDriver driver, WebElement element, String name) {
		super(driver, element, name);
	}
	
	protected Upload(WebDriver driver, WebElement element, String name, int timeout) {
		super(driver, element, name, timeout);
	}
	
	public void fileExists(File file) throws Exception {
		if(!file.exists() && !file.isFile()) {
			LOGGER.severe("Problmes finding the given path " + file.getAbsolutePath());
			throw new Exception("The given path does not existe!");
		}
	}
	
	public boolean uploadFile(String pathFile) {
		File file;
		boolean status = false;
		try {
			file = new File(pathFile);
		}
		catch(Exception e) {
			LOGGER.severe("Unable to create a file object based on the given pathFile " + e.getMessage());
			throw e;
		}
		try {
			fileExists(file);
			element.sendKeys(file.getAbsolutePath());
			status = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return status;
	}
}
