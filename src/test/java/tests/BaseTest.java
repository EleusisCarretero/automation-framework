package tests;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.logging.Logger;
import org.json.JSONObject;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import pages.BasePage;
import ui.Button;
import ui.CheckboxButton;
import ui.DropDownElement;
import ui.InputElement;
import ui.RadioButton;
import ui.TextElement;
import ui.Upload;
import utilities.ApiConnecter;
import utilities.AssertManager;


public class BaseTest {
	protected static final Logger LOGGER = Logger.getLogger(BaseTest.class.getName());
	protected static int stepNum = 1;
	protected static List<Integer> advertCoors = List.of(30, 900);
	public WebDriver driver;
	private BasePage page;
	protected AssertManager assertManager;
	
	protected boolean setup(BasePage page) {
		this.page = page;
		this.assertManager = new AssertManager();
		return this.page.launchPage();
	}
	
	protected boolean teardown() {
		return page.quitPage();
	}
	
	public int stepNum() {
		stepNum ++;
		return stepNum;
	}
	protected void stepMsg(String stepMsg) {
		LOGGER.info("STEP " + stepNum() + ": " + stepMsg);
	}
	
	protected String getNeededHdl(int position) {
		Set<String> handlers = this.driver.getWindowHandles();
		String[] hdlArray = handlers.toArray(new String[0]);
		return hdlArray[position];
	}
	
	public String switchContext(int position) {
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
	
	protected void checkInputFill(InputElement inputElement, String input) {
		stepMsg("Check input element" + inputElement.name() + "has the expected value");
		this.assertManager.checkEqualsTo(inputElement.text(), input);
	}
	
	@SuppressWarnings("unchecked")
	protected
	void verifyInputFill(InputElement inputElement, String input, boolean clean) {
		// 1.- Set the value
		String errorMessage = "While trying to write into element " + inputElement.name() + " an excpetion has happend";
		stepMsg("Verify input element" + inputElement.name() + "has been filled succesfully");
		this.assertManager.assertNotThrowsOfType(() -> {
			inputElement.write(input, clean);
		},errorMessage,
		NoSuchElementException.class, TimeoutException.class);
		// 2.- Verify content of input element
		checkInputFill(inputElement, input);
	}
	
	@SuppressWarnings("unchecked")
	public void verifySimpleClick(Button buttonElement) {
		// Try to execute normal click
		String errorMessage = "While trying to click on element" + buttonElement.name() + " an expcetion has happend";
		stepMsg("Verify simple click on " + buttonElement.name() + "has been exceuted successfully");
		this.assertManager.assertNotThrowsOfType(() -> {
			buttonElement.click();
		},errorMessage,
		NoSuchElementException.class, StaleElementReferenceException.class, TimeoutException.class, ElementClickInterceptedException.class);
	}
	
	protected void verifyFillAndClick(List<Map <InputElement, String>> inputMapList, Button buttonElement, boolean openPage) {
		// 1. fill all the input elements and validate they have been correctly executed
		stepMsg("Verify the whole input elements list is filled and button click executede successfully");
		for (Map <InputElement, String> inputMap: inputMapList) {
			inputMap.forEach((inputElement, input) -> {
				verifyInputFill(inputElement, input, true);
			});
		}
		awaiting(3);
		if (openPage) {
			// 2. Click button and check that the url has changed
			stepVerifyPageOpenAfterClickBtn(buttonElement);
		}
		else {
			// 2. Execute simple click
			verifySimpleClick(buttonElement);
		}
	}
	
	@SuppressWarnings("unchecked")
	protected
	void verifyDropDownSet(DropDownElement dropDownElement, String expectedValue, boolean byVisibleText) {
		// 1 Set dropdown value
		String errorMessage = "While trying to set dropdown element" + dropDownElement.name() + "An expcetion has happend";
		stepMsg("Verify that the " + dropDownElement.name() + "DropDown element has been set succesfully");
		this.assertManager.assertNotThrowsOfType(() -> {
			dropDownElement.set(expectedValue, byVisibleText);
		},errorMessage,
		NoSuchElementException.class);
		awaiting(1);
		// 2 read the actual value
		String currentValue = dropDownElement.get(byVisibleText);
		this.assertManager.checkEqualsTo(currentValue, expectedValue);
	}
	
	@SuppressWarnings("unchecked")
	void verifyDropDownSet(DropDownElement dropDownElement, String expectedValue) {
		// 1 Set dropdown value
		String errorMessage = "While trying to set dropdown element" + dropDownElement.name() + "an expcetion has happend";
		stepMsg("Verify that the " + dropDownElement.name() + "DropDown element has been set successfully");
		this.assertManager.assertNotThrowsOfType(() -> {
			dropDownElement.set(Integer.parseInt(expectedValue));
		},errorMessage,
		NoSuchElementException.class);
		// 2 read the actual value
		String currentValue = dropDownElement.get(false);
		this.assertManager.checkEqualsTo(currentValue, expectedValue);
	}
	
	protected void verifyMultipleDropDownSet(List<DropDownElement> dropDownList, List<String> birthdayFields, List<Boolean> visibleFlags) {
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
	
	protected void checkTextElemtValue(TextElement textElement, String expectedValue) {
		stepMsg("Verify that the " + textElement.name() + "Text element has correct value");
		this.assertManager.checkEqualsTo(textElement.text(), expectedValue);
	}
	
	protected void verifyRadioBtnChecked(RadioButton radioElement) {
		stepMsg("Verify that the " + radioElement.name() + "is checked in the expected status");
		boolean currentStatus = radioElement.isChecked();
		if(!currentStatus) {
			LOGGER.info(radioElement.name() + "is un checked. Checking it.");
			radioElement.check();
			awaiting(1);
		}
		this.assertManager.checkIsTrue(radioElement.isChecked());
	}
	
	void checkRadioBtnUnChecked(RadioButton radioElement) {
		stepMsg("Verify that the " + radioElement.name() + "is uncheck in the expected status");
		this.assertManager.checkIsFalse(radioElement.isChecked());
	}
	
	protected void verifyCheckboxButton(CheckboxButton checkboxButtonElement, boolean expectedStatus) {
		stepMsg("Verify that the " + checkboxButtonElement.name() + "is check as its expected status");
		boolean currentStatus = checkboxButtonElement.isChecked();
		if(currentStatus != expectedStatus) {
			LOGGER.info(checkboxButtonElement.name() + "is not its expected status. Changing it.");
			checkboxButtonElement.check();
			awaiting(1);
		}
		this.assertManager.checkIsTrue(checkboxButtonElement.isChecked());
	}
	
	public void checkUrl(String expectedUrl, boolean isEquals) {
		String currentURl = switchContext(0);
		if (isEquals) {this.assertManager.checkEqualsTo(currentURl, expectedUrl);}
		else {this.assertManager.CheckNotEqualsTo(currentURl, expectedUrl);}
	}
	
	protected void stepVerifyPageOpenAfterClickBtn(Button button) {
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
			e.printStackTrace();
		}
		return apiCon.filterFields(response, fields);
	}
	
	public void StepCheckFileUploaded(Upload upload, String filepath) {
		boolean status = false;
		stepMsg("Check the file " + filepath + " has been correctly updated");
		try {
			status = upload.uploadFile(filepath);
		}
		catch(Exception e) {
			LOGGER.severe("Unable to updload file: " + filepath + e.getMessage());
		}
		this.assertManager.checkIsTrue(status);
	}
	
	public void checkCloseAvdertising(BasePage page, int coX, int coY) {
		LOGGER.info("Closing advertising from coordinates: [" + coX + ", "+ coY + "]");
		page.clickOnCoordinates(coX, coY);
		awaiting(2);
	}
	

}
