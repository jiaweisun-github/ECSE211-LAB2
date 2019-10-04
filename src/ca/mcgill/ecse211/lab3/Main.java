package ca.mcgill.ecse211.lab3;
import lejos.hardware.Button;


/**
 * The main driver class for the odometry lab.
 */
public class Main {

	/**
	 * The main entry point.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		int buttonChoice;
		new Thread(Resources.odometer).start();
		buttonChoice = chooseNavigationMode();
		new Thread(new Display()).start();

		if (buttonChoice == Button.ID_LEFT) {
			new Thread(new Navigation(Resources.odometer,Resources.leftMotor,Resources.rightMotor)).start();
		}       
		else if (buttonChoice == Button.ID_RIGHT) {
			new Thread(new NavigationObstacle()).start(); // TODO implement OdometryCorrection
		}

		new Thread(new Display()).start();
		while (Button.waitForAnyPress() != Button.ID_ESCAPE) {
		} // do nothing

		System.exit(0);
	}

	/**
	 * Asks the user whether cthey want to choose navigation or navigation with obstacles.
	 * 
	 * @return the user choice
	 */
	private static int chooseNavigationMode() {
		int buttonChoice;
		Display.showText("< Left | Right >",
				"       |        ",
				" Navi- | Navi-  ",
				"gation | gation ",
				"       | dodge ");

		do {
			buttonChoice = Button.waitForAnyPress(); // left or right press
		} while (buttonChoice != Button.ID_LEFT && buttonChoice != Button.ID_RIGHT);
		return buttonChoice;
	}

	/**
	 * Sleeps current thread for the specified duration.
	 * 
	 * @param duration sleep duration in milliseconds
	 */
	public static void sleepFor(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			// There is nothing to be done here
		}
	}


}
