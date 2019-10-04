package ca.mcgill.ecse211.lab3;										

public class NavigationObstacle implements Runnable{
	public void run() {
																					//THE CODE DOESNT RUN
	}
//
//	private static NavigationObstacle navigation;
//	private static Odometer odo;
//	private static EV3LargeRegulatedMotor leftMotor, rightMotor;
//	private static final double pi = Math.PI;
//	private static boolean navigating = false;
//	static double xDistance;
//	static double yDistance;
//	static double distance;
//	static double angle;
//	double angleRad;
//	double positiveTheta;
//
//	public NavigationObstacle() {
//		
//	}
//
//	/**
//	 * Get instance of the Navigation class. Only allows one thread at a time
//	 * calling this method.
//	 */
//	public synchronized static NavigationObstacle getnavigation() {
//		if (navigation == null) {
//			navigation = new NavigationObstacle();
//		}
//		return navigation;
//	}
//	
//@Override
//	public void run() {
//	
//	double TILE_SIZE = Resources.TILE_SIZE;
//	// Input travel points here
////	travelTo((2 * TILE_SIZE), (2 * TILE_SIZE));
////	travelTo((1 * TILE_SIZE), (2 * TILE_SIZE));
////	travelTo((2 * TILE_SIZE), (2 * TILE_SIZE));
////	travelTo((0 * TILE_SIZE), (1 * TILE_SIZE));
//	travelTo((2 * TILE_SIZE), (3 * TILE_SIZE));
//	travelTo((0 * TILE_SIZE), (1 * TILE_SIZE));
//	travelTo((3 * TILE_SIZE), (2 * TILE_SIZE));
//	travelTo((0 * TILE_SIZE), (3 * TILE_SIZE));
//	travelTo((3 * TILE_SIZE), (0 * TILE_SIZE));
//
//			// Sleep while it is traveling
//			while (navigation.isNavigating()) {
//				Main.sleepFor(500);
//				}
//			
//		}
//	
//
//	public static void travelTo(double x, double y) {
//		leftMotor.setSpeed(0);;
//		rightMotor.setSpeed(0);
//		leftMotor.setAcceleration(3000);
//		rightMotor.setAcceleration(3000);
//
//		navigating = true;
//
//		// Calculate path and angle
//		xDistance = x - odo.getXYT()[0];
//		yDistance = y - odo.getXYT()[1];
//		distance = Math.sqrt(Math.pow(xDistance, 2)+Math.pow(yDistance, 2));
//
//		if(xDistance > 0 && yDistance > 0) {
////			if(odo.getXYT()[2]<0) {
////				//					positiveTheta = odo.getXYT()[2] + 2*pi;
////			}
//			angle = pi/2 - Math.toRadians(odo.getXYT()[2]) - Math.atan(yDistance/xDistance); //DONEEEEEE							//positive x and y, 1ST QUADRANT GOOD
//
//		}
//		else if(xDistance < 0 && yDistance > 0) {													//negative x and positive y, 2ND QUADRANT GOOD
//			
//			angle = 3*pi/2 - Math.toRadians(odo.getXYT()[2]) - Math.atan(yDistance/xDistance);
//
//		}
//		else if(xDistance < 0 && yDistance < 0) {													//negative x and negative y, 3RD QUADRANT GOOD
//
//			angle = 3*pi/2 - Math.toRadians(odo.getXYT()[2]) - Math.atan(yDistance/xDistance);
//		}
//		else if(xDistance > 0 && yDistance < 0) {													//positive x and negative y, 4TH QUADRANT GOOD
//
//			angle = pi/2 - Math.toRadians(odo.getXYT()[2]) - Math.atan(yDistance/xDistance);
//		}
//		System.out.println("           "+angle);
//
//		// Turn to face the waypoint
//		int ROTATE_SPEED = Resources.ROTATE_SPEED;
//		leftMotor.setSpeed(ROTATE_SPEED);
//		rightMotor.setSpeed(ROTATE_SPEED);
//		turnBy(angle);
//
//		// Advance forward equal to path distance
//		int FORWARD_SPEED = Resources.FORWARD_SPEED;
//
//		leftMotor.setSpeed(FORWARD_SPEED);
//		rightMotor.setSpeed(FORWARD_SPEED);
//		leftMotor.rotate(distanceToRotations(distance), true);
//		rightMotor.rotate(distanceToRotations(distance), false);
//		
//	}
//
//	private boolean isNavigating() {
//		/**
//		 * This method returns true if another thread has called travelTo() or turnTo()
//		 * and the method has yet to return; false otherwise.
//		 */
//		return navigating;
//	}
//
//	private static void turnBy(double theta) {
//		/**
//		 * This method causes the robot to turn (on point) to the absolute heading theta.
//		 * This method should turn a MINIMAL angle to its target.
//		 */
//		// Set rotate speed
//		leftMotor.setSpeed(Resources.ROTATE_SPEED);
//		rightMotor.setSpeed(Resources.ROTATE_SPEED);
//
//		if (theta < 0) {
//			// If angle is negative, turn left
//			leftMotor.rotate(convertAngle(theta), true);
//			rightMotor.rotate(-convertAngle(theta), false);
//		} else {
//			// If angle is positive, turn right
//			leftMotor.rotate(convertAngle(theta), true);
//			rightMotor.rotate(-convertAngle(theta), false);
//		}
//	}
//
//	/**
//	 * Converts input distance to the total rotation of each wheel needed to cover
//	 * that distance.
//	 * 
//	 * @param distance
//	 * @return the wheel rotations necessary to cover the distance
//	 */
//	
//
//	public static int distanceToRotations(double distance) {
//		return (int) (180 * distance / (pi * Resources.WHEEL_RAD));
//	}
//	/**
//	 * Converts input angle to the total rotation of each wheel needed to rotate the
//	 * robot by that angle.
//	 * 
//	 * @param angle
//	 * @return the wheel rotations necessary to rotate the robot by the angle
//	 */
//	public static int convertAngle(double angle) {
//		return distanceToRotations(Math.PI * Resources.TRACK * angle / 360.0);
//	}
//	
//	/**
//	 * Setter method for traveling
//	 * @param traveling
//	 */
//	public static void setNavigating(boolean navigating) {
//		NavigationObstacle.navigating = navigating;
//	}
}
