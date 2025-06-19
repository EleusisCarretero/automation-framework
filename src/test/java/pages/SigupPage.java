package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SigupPage extends BasePage {
	
	// ENTER ACCOUNT INFORMATION LOCATOR
	
	// data birthday
	@FindBy (id="days")
	WebElement ddDays;
	
	@FindBy (id="months")
	WebElement ddMonths;
	
	@FindBy (id="years")
	WebElement ddYears;
	
	public SigupPage(WebDriver driver, String url) {
		super(driver, url);
		// TODO Auto-generated constructor stub
	}
	
	public void ddDays(String day) {
		try {
			selectDropDown(ddDays, day, false);
		}
		catch(InterruptedException e) {
		}
		
	}
	
	public String ddDays() {
		return getDropDownValue(ddDays);
	}
	
	public void ddMonths(String month)  {
		try {
			selectDropDown(ddMonths, month, true);
		}
		catch(InterruptedException e) {
		}
	}
	
	public String ddMonths() {
		return getDropDownValue(ddMonths);
	}
	
	public void ddYears(String year) {
		try {
			selectDropDown(ddYears, year, false);
		}
		catch(InterruptedException e) {
		}
	}
	
	public String ddYears() {
		return getDropDownValue(ddYears);
	}

}
