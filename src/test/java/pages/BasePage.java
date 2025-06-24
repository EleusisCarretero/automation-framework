package pages;
import java.time.Duration;
import java.util.logging.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;
import utilities.CommonTools;



public class BasePage {
	protected static final Logger LOGGER = Logger.getLogger(BasePage.class.getName());
	WebDriver driver;
	WebDriverWait wait;
	CommonTools helpetool;
	String URL;
	int TIMEOUT = 10;
	
	public BasePage(WebDriver driver, String url) {
		this.driver =  driver;
		this.driver.manage().window().maximize();
		this.URL = url;
		wait = new WebDriverWait(this.driver, Duration.ofSeconds(TIMEOUT));
		this.helpetool = new CommonTools();
		PageFactory.initElements(this.driver, this);
	}
	
	public BasePage(WebDriver driver, String url, int timeout) {
		this.driver =  driver;
		this.URL = url;
		this.TIMEOUT = timeout;
		wait = new WebDriverWait(this.driver, Duration.ofSeconds(TIMEOUT));
		PageFactory.initElements(this.driver, this);
	}
	
	public boolean launchPage() {
		try {
			this.driver.get(this.URL);
			System.out.println(this.URL + "Correclty launched");
			return true;
		}
		catch(Exception e) {
			System.out.println("Unabel to launch page");
			return false;
		}
	}
	
	public boolean quitPage() {
		try {
			this.driver.quit();
			System.out.println(this.URL + "Correctly quit");
			return true;
		}
		catch(Exception e) {
			System.out.println("Unabel to quite page");
			return false;
		}
	}
	public String url() {
		return URL;
	}
	
	public void clickOnCoordinates(int coX, int coY) {
		try {
			this.helpetool.clickCoordinates(coX, coY);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
