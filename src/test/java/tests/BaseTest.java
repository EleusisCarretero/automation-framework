package tests;

import java.time.Duration;
import java.util.Set;
import java.util.logging.Logger;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.google.common.base.Supplier;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

import io.opentelemetry.internal.shaded.jctools.queues.MessagePassingQueue.Consumer;
import pages.BasePage;
import utilities.DataBaseReader;

public class BaseTest {
	private static final Logger LOGGER = Logger.getLogger(DataBaseReader.class.getName());
	WebDriver driver;
	private BasePage page;
	
	boolean setup(BasePage page) {
		this.page = page;
		return this.page.launchPage();
	}
	
	boolean teardown() {
		return page.quitPage();
	}
	
	void verifyDatawritten(String input, Consumer<String> writer, Supplier<String> reader) {
		LOGGER.info("Write "+ input+ " in the desire field");
		// 1 write data
		writer.accept(input);
		// 2 read actual info
		String actualinfo = reader.get();
		// 3 compare
		Assert.assertEquals(input, actualinfo);
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
	
	void verifyDropDown(String value, Consumer<String> writer, Supplier<String> reader) throws InterruptedException  {
		LOGGER.info("Verify "+ value+ " is correclty set in the drop-down");
		//1 set data
		writer.accept(value);
		Thread.sleep(Duration.ofSeconds(1));
		//2 read data
		String actualvalue = reader.get();
		//3 compare
		Assert.assertEquals(value, actualvalue);
	}

}
