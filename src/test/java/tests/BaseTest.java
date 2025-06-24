package tests;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Logger;

import org.json.JSONObject;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.google.common.base.Supplier;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import io.opentelemetry.internal.shaded.jctools.queues.MessagePassingQueue.Consumer;
import pages.BasePage;
import utilities.ApiConnecter;
import utilities.AssertManager;
import utilities.Button;
import utilities.CheckboxButton;
import utilities.DataBaseReader;
import utilities.DropDownElement;
import utilities.InputElement;
import utilities.RadioButton;
import utilities.TextElement;

public class BaseTest {
	protected static final Logger LOGGER = Logger.getLogger(DataBaseReader.class.getName());
	protected static int stepNum = 1;
	WebDriver driver;
	private BasePage page;
	protected AssertManager assertManager;
	
	boolean setup(BasePage page) {
		this.page = page;
		this.assertManager = new AssertManager();
		return this.page.launchPage();
	}
	
	boolean teardown() {
		return page.quitPage();
	}
	
	public int stepNum() {
		stepNum ++;
		return stepNum;
	}
	void stepMsg(String stepMsg) {
		LOGGER.info("STEP " + stepNum() + ": " + stepMsg);
	}
	
	String getNeededHdl(int position) {
		Set<String> handlers = this.driver.getWindowHandles();
		String[] hdlArray = handlers.toArray(new String[0]);
		return hdlArray[position];
	}
	
	String switchContext(int position) {
		String currentHdlr = getNeededHdl(position);
		this.driver.switchTo().window(currentHdlr);
		return this.driver.getCurrentUrl();
		
	}
	
	protected void awaiting(int awatingTime) {
		try {
			Thread.sleep(Duration.ofSeconds(awatingTime));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	void checkInputFill(InputElement inputElement, String input) {
		stepMsg("Check input element" + inputElement.name() + "has the expected value");
		this.assertManager.checkEqualsTo(inputElement.text(), input);
	}
	
	@SuppressWarnings("unchecked")
	void verifyInputFill(InputElement inputElement, String input, boolean clean) {
		// 1.- Set the value
		stepMsg("Verify input element" + inputElement.name() + "has been filled succesfully");
		this.assertManager.assertNotThrowsOfType(() -> {
			inputElement.write(input, clean);
		}, NoSuchElementException.class, TimeoutException.class);
		// 2.- Verify content of input element
		checkInputFill(inputElement, input);
	}
	
	void verifySimpleClick(Button buttonElement) {
		// Try to execute normal click
		stepMsg("Verify simple click on " + buttonElement.name() + "has been exceuted successfully");
		try {
			buttonElement.click();
		}
		catch(NoSuchElementException | StaleElementReferenceException | TimeoutException | ElementClickInterceptedException e) {
			Assert.fail("While trying to click on element" + buttonElement.name() + "An expcetion has happend", e);
		}
	}
	
	void verifyFillAndClick(List<Map <InputElement, String>> inputMapList, Button buttonElement) {
		// 1. fill all the input elements and validate they have been correctly executed
		stepMsg("Verify the whole input elements list is filled and button click executede successfully");
		for (Map <InputElement, String> inputMap: inputMapList) {
			inputMap.forEach((inputElement, input) -> {
				verifyInputFill(inputElement, input, true);
			});
		}
		awaiting(3);
		// 2. Execute simple click
		verifySimpleClick(buttonElement);
	}
	
	void verifyDropDownSet(DropDownElement dropDownElement, String expectedValue, boolean byVisibleText) {
		// 1 Set dropdown value
		stepMsg("Verify that the " + dropDownElement.name() + "DropDown element has been set succesfully");
		try {
			dropDownElement.set(expectedValue, byVisibleText);
			awaiting(1);
		}
		catch(NoSuchElementException e) {
			Assert.fail("While trying to set dropdown element" + dropDownElement.name() + "An expcetion has happend", e);
		}
		// 2 read the actual value
		String currentValue = dropDownElement.get(byVisibleText);
		Assert.assertEquals(
				currentValue,
				expectedValue,
				"The actual value " + currentValue + "is not equals to " + expectedValue);
	}
	
	void verifyDropDownSet(DropDownElement dropDownElement, String expectedValue) {
		// 1 Set dropdown value
		stepMsg("Verify that the " + dropDownElement.name() + "DropDown element has been set successfully");
		try {
			dropDownElement.set(Integer.parseInt(expectedValue));
		}
		catch(NoSuchElementException e) {
			Assert.fail("While trying to set dropdown element" + dropDownElement.name() + "An expcetion has happend", e);
		}
		// 2 read the actual value
		String currentValue = dropDownElement.get(false);
		Assert.assertEquals(currentValue, expectedValue);
	}
	
	void verifyMultipleDropDownSet(List<DropDownElement> dropDownList, List<String> birthdayFields, List<Boolean> visibleFlags) {
		for (int i = 0; i < dropDownList.size(); i++) {
		    verifyDropDownSet(dropDownList.get(i), birthdayFields.get(i), visibleFlags.get(i));
		    awaiting(1);
		}
	}
	void verifyMultipleDropDownSet(List<DropDownElement> dropDownList, List<String> birthdayFields) {
		for (int i = 0; i < dropDownList.size(); i++) {
		    verifyDropDownSet(dropDownList.get(i), birthdayFields.get(i));
		    awaiting(1);
		}
	}
	
	void checkTextElemtValue(TextElement textElement, String expectedValue) {
		stepMsg("Verify that the " + textElement.name() + "Text element has correct value");
		Assert.assertEquals(textElement.text(), expectedValue);
	}
	
	void verifyRadioBtnChecked(RadioButton radioElement) {
		stepMsg("Verify that the " + radioElement.name() + "is checked in the expected status");
		boolean currentStatus = radioElement.isChecked();
		if(!currentStatus) {
			LOGGER.info(radioElement.name() + "is un checked. Checking it.");
			radioElement.check();
			awaiting(1);
		}
		Assert.assertEquals(radioElement.isChecked(), true);
	}
	
	void checkRadioBtnUnChecked(RadioButton radioElement) {
		stepMsg("Verify that the " + radioElement.name() + "is uncheck in the expected status");
		Assert.assertEquals(radioElement.isChecked(), false);
	}
	
	void verifyCheckboxButton(CheckboxButton checkboxButtonElement, boolean expectedStatus) {
		stepMsg("Verify that the " + checkboxButtonElement.name() + "is check as its expected status");
		boolean currentStatus = checkboxButtonElement.isChecked();
		if(currentStatus != expectedStatus) {
			LOGGER.info(checkboxButtonElement.name() + "is not its expected status. Changing it.");
			checkboxButtonElement.check();
			awaiting(1);
		}
		Assert.assertEquals(checkboxButtonElement.isChecked(), expectedStatus);
	}
	
	public void checkUrl(String expectedUrl, boolean isEquals) {
		String currentURl = switchContext(0);
		if (isEquals) {Assert.assertEquals(currentURl, expectedUrl);}
		else {Assert.assertNotEquals(currentURl, expectedUrl);}
	}
	
	void stepVerifyPageOpenAfterClickBtn(Button button, BasePage page) {
		// 1 get current url
		String url = switchContext(0);
		// 2. Click button
		stepMsg("Verify page has been opend after clicking button" + button.name() + "And the url has change");
		button.click();
		awaiting(2);
		// 3. change handler
		checkUrl(url, false);
	}
	
	public Map<String, Object> getUserData(List<String> fields){
		JSONObject response = null;
		ApiConnecter apiCon = new ApiConnecter("https://randomuser.me/api/");
		try {
			response = apiCon.getQuery();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return apiCon.filterFields(response, fields);
	}
	

}
