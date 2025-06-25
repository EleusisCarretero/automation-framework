package utilities;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;

import java.time.Duration;

public class CommonTools {
	Robot robot;
	public CommonTools() {
		try {
			this.robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	public void clickCoordinates(int coX, int coY) throws InterruptedException {
		// 1. move to coordinates
		this.robot.mouseMove(coX, coY);
		// 2. clikc on the coordinates
		//robot.mousePress(InputEvent.BUTTON3_DOWN_MASK);
		this.robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		Thread.sleep(Duration.ofSeconds(1));
		// 3. Release on the coordinates
		this.robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
}
