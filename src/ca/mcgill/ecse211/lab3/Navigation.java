package ca.mcgill.ecse211.lab3;

import lejos.hardware.motor.EV3LargeRegulatedMotor;

public class Navigation extends Thread {

	private Odometer odo;
	private EV3LargeRegulatedMotor leftMotor, rightMotor;
	private static final double pi = Math.PI;
	private static boolean navigating = false;

	//distance and theta calculation parameters
	double xDistance;
	double yDistance;
	double distance;
	double angle = 0;


	public Navigation(Odometer odo, EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor) {			//constructor with odometer and motors
		this.odo = odo;
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
	}

	@Override
	public void run() {

		double TILE_SIZE = Resources.TILE_SIZE;
		//input travel coordinates
		
		//		travelTo((1 * TILE_SIZE-TILE_SIZE), (3 * TILE_SIZE-TILE_SIZE));						//path 1
		//		travelTo((2 * TILE_SIZE-TILE_SIZE), (2 * TILE_SIZE-TILE_SIZE));
		//		travelTo((3 * TILE_SIZE-TILE_SIZE), (3 * TILE_SIZE-TILE_SIZE));
		//		travelTo((3 * TILE_SIZE-TILE_SIZE), (2 * TILE_SIZE-TILE_SIZE));
		//		travelTo((2 * TILE_SIZE-TILE_SIZE), (1 * TILE_SIZE-TILE_SIZE));

		travelTo((3 * TILE_SIZE-TILE_SIZE), (2 * TILE_SIZE-TILE_SIZE));								//path 2
		travelTo((2 * TILE_SIZE-TILE_SIZE), (2 * TILE_SIZE-TILE_SIZE));
		travelTo((2 * TILE_SIZE-TILE_SIZE), (3 * TILE_SIZE-TILE_SIZE));
		travelTo((3 * TILE_SIZE-TILE_SIZE), (1 * TILE_SIZE-TILE_SIZE));
//		travelTo((2 * TILE_SIZE-TILE_SIZE), (1 * TILE_SIZE-TILE_SIZE));
		//		
		//		travelTo((2 * TILE_SIZE-TILE_SIZE), (1 * TILE_SIZE-TILE_SIZE));						//path 3
		//		travelTo((3 * TILE_SIZE-TILE_SIZE), (2 * TILE_SIZE-TILE_SIZE));
		//		travelTo((3 * TILE_SIZE-TILE_SIZE), (3 * TILE_SIZE-TILE_SIZE));
		//		travelTo((1 * TILE_SIZE-TILE_SIZE), (3 * TILE_SIZE-TILE_SIZE));
		//		travelTo((2 * TILE_SIZE-TILE_SIZE), (2 * TILE_SIZE-TILE_SIZE));
		//		
		//		travelTo((1 * TILE_SIZE-TILE_SIZE), (2 * TILE_SIZE-TILE_SIZE));						//path 4
		//		travelTo((2 * TILE_SIZE-TILE_SIZE), (3 * TILE_SIZE-TILE_SIZE));
		//		travelTo((2 * TILE_SIZE-TILE_SIZE), (1 * TILE_SIZE-TILE_SIZE));
		//		travelTo((3 * TILE_SIZE-TILE_SIZE), (2 * TILE_SIZE-TILE_SIZE));
		//		travelTo((3 * TILE_SIZE-TILE_SIZE), (3 * TILE_SIZE-TILE_SIZE));
	}

	public void travelTo(double x, double y) {

		leftMotor.setSpeed(0);;
		rightMotor.setSpeed(0);
		leftMotor.setAcceleration(3000);
		rightMotor.setAcceleration(3000);

		navigating = true;

		// Calculate path and angle
		xDistance = x - odo.getXYT()[0];
		yDistance = y - odo.getXYT()[1];
		distance = Math.sqrt(Math.pow(xDistance, 2)+Math.pow(yDistance, 2));

		if(xDistance > 0 && yDistance > 0) {														//positive x and y, 1ST QUADRANT GOOD
			angle = pi/2 - Math.toRadians(odo.getXYT()[2]) - Math.atan(yDistance/xDistance);							
		}
		else if(xDistance < 0 && yDistance > 0) {													//negative x and positive y, 2ND QUADRANT GOOD	
			angle = 3*pi/2 - Math.toRadians(odo.getXYT()[2]) - Math.atan(yDistance/xDistance);
		}
		else if(xDistance < 0 && yDistance < 0) {													//negative x and negative y, 3RD QUADRANT GOOD
			angle = 3*pi/2 - Math.toRadians(odo.getXYT()[2]) - Math.atan(yDistance/xDistance);
		}
		else if(xDistance > 0 && yDistance < 0) {													//positive x and negative y, 4TH QUADRANT GOOD
			angle = pi/2 - Math.toRadians(odo.getXYT()[2]) - Math.atan(yDistance/xDistance);
		}
		// Turn to the theta of next coordinate
		int ROTATE_SPEED = Resources.ROTATE_SPEED;
		leftMotor.setSpeed(ROTATE_SPEED);
		rightMotor.setSpeed(ROTATE_SPEED);
		turnBy(angle);

		// Displace until reaching the next coordinate
		int FORWARD_SPEED = Resources.FORWARD_SPEED;

		leftMotor.setSpeed(FORWARD_SPEED);
		rightMotor.setSpeed(FORWARD_SPEED);
		leftMotor.rotate(distanceToRotations(distance), true);
		rightMotor.rotate(distanceToRotations(distance), false);
	}

	public void turnBy(double theta) {																//if calculated angle too big or too small, adjust its value
		double myAngle = theta;																		//and get minimum angle
		if (myAngle > pi) {
			myAngle = -(2*pi - angle);
		} 
		else if(myAngle > 2*pi) {
			myAngle = -(3*pi - angle);
		}
		else if(myAngle > 3*pi) {
			myAngle = -(4*pi - angle);
		}
		else if(myAngle > 4*pi) {
			myAngle = -(5*pi - angle);
		}
		else if (myAngle < -pi) {
			myAngle = 2 * pi + angle;
		}
		else if (myAngle < -2*pi) {
			myAngle = 3 * pi + angle;
		}
		else if (myAngle < -3*pi) {
			myAngle = 4 * pi + angle;
		}
		else if (myAngle < -4*pi) {
			myAngle = 5 * pi + angle;
		}

		leftMotor.rotate(radianToDegree(myAngle), true);											//rotate to the desired theta
		rightMotor.rotate(-(radianToDegree(myAngle)), false);
	}

	public int distanceToRotations(double distance) {												//convert the distance required to turns 
		return (int) (180 * distance / (pi * Resources.WHEEL_RAD));
	}

	public int radianToDegree(double angle) {														//convert radians to degrees
		return distanceToRotations(Resources.TRACK * angle / 2);
	}

	public boolean isNavigating() {																	//check whether the navigation is running or not
		return navigating;
	}

}

