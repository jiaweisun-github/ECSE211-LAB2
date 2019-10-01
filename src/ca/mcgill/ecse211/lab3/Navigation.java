package ca.mcgill.ecse211.lab3;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
//import ca.mcgill.ecse211.lab3.Resources.*;


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
		/**
		 * Constructor
		 * 
		 * @param odo
		 * @param leftMotor
		 * @param rightMotor
		 */
		public Navigation(Odometer odo, EV3LargeRegulatedMotor leftMotor, EV3LargeRegulatedMotor rightMotor) {
			this.odo = odo;
			this.leftMotor = leftMotor;
			this.rightMotor = rightMotor;
		}

		@Override
		public void run() {
			
			double TILE_SIZE = Resources.TILE_SIZE;
			// Input travel points here
			travelTo((1 * TILE_SIZE), (1 * TILE_SIZE));
			travelTo((0 * TILE_SIZE), (2 * TILE_SIZE));
			travelTo((2 * TILE_SIZE), (2 * TILE_SIZE));
			travelTo((2 * TILE_SIZE), (1 * TILE_SIZE));
			travelTo((1 * TILE_SIZE), (0 * TILE_SIZE));
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
//			if(xDistance > 0 && yDistance > 0) {														//positive x and y, 1ST QUADRANT
//				angle = (pi/2)-Math.atan(yDistance/xDistance);
//			}
//			else if(xDistance < 0 && yDistance > 0) {													//negative x and positive y, 2ND QUADRANT
//				angle = -((pi/2)+Math.atan(yDistance/xDistance));
//			}
//			else if(xDistance < 0 && yDistance < 0) {													//negative x and negative y, 3RD QUADRANT
//				angle = -((pi/2)+Math.atan(yDistance/xDistance));
//			}
//			else if(xDistance > 0 && yDistance < 0) {													//positive x and negative y, 4TH QUADRANT
//				angle = (pi/2)-Math.atan(yDistance/xDistance);
//			}
			if(xDistance > 0 && yDistance > 0) {														//positive x and y, 1ST QUADRANT GOOD
				angle = (pi/2)-Math.atan(xDistance/yDistance);
			}
			else if(xDistance < 0 && yDistance > 0) {													//negative x and positive y, 2ND QUADRANT GOOD
				angle = (pi/2)-Math.atan(xDistance/yDistance);
			}
			else if(xDistance < 0 && yDistance < 0) {													//negative x and negative y, 3RD QUADRANT GOOD
				angle = -((pi/2)+Math.atan(xDistance/yDistance));
			}
			else if(xDistance > 0 && yDistance < 0) {													//positive x and negative y, 4TH QUADRANT GOOD
				angle = -((pi/2)+Math.atan(xDistance/yDistance));
			}
//			else if(xDistance == 0 && yDistance > 0) {													//if no x or no y displacement??
//				
//			}
//			else if(yDistance == 0) {
//				
//			}

			// Turn to face the waypoint
			int ROTATE_SPEED = Resources.ROTATE_SPEED;
			leftMotor.setSpeed(ROTATE_SPEED);
			rightMotor.setSpeed(ROTATE_SPEED);
			turnTo(angle);

			// Advance forward equal to path distance
			int FORWARD_SPEED = Resources.FORWARD_SPEED;

			leftMotor.setSpeed(FORWARD_SPEED);
			rightMotor.setSpeed(FORWARD_SPEED);
			leftMotor.rotate(distanceToRotations(distance), true);
			rightMotor.rotate(distanceToRotations(distance), false);
		}

		public void turnTo(double theta) {
			double myAngle = 0;
			if (theta - odo.getXYT()[2] > pi) {											//if the angle theta at which it turns is bigger than pi, new angle = angle - 2*pi
				myAngle = angle - 2 * pi;
			} else if (theta - odo.getXYT()[2] < -pi) {
				myAngle = 2 * pi+ angle;
			}
			leftMotor.rotate(distanceToRotations(Resources.TRACK*myAngle/2), true);
			rightMotor.rotate(-(distanceToRotations(Resources.TRACK*myAngle/2)), false);
		}

		public int distanceToRotations(double distance) {
			return (int) (180 * distance / (pi * Resources.WHEEL_RAD));
		}

//		public int radianToDegree(double angle) {
//			return distanceToRotations(Resources.TRACK * angle / 2);
//		}
		
		/**
		 * Checks to see if the robot is on the move or not
		 * 
		 * @return true/false
		 */
		public boolean isNavigating() {
			return navigating;
		}

	}

