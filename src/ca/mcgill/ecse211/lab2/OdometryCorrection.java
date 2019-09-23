package ca.mcgill.ecse211.lab2;

import static ca.mcgill.ecse211.lab2.Resources.*;

import lejos.hardware.Sound;
//import lejos.robotics.SampleProvider;
import lejos.robotics.SampleProvider;

public class OdometryCorrection implements Runnable {
	
  private static final long CORRECTION_PERIOD = 10;
  public float[] dataArray;
  private SampleProvider colorDetector;
  public static double theta;
  public static int counterX;
  public static int counterY;
  public static double tempXCoordinate;
  public static double tempYCoordinate;
  public Odometer odo;
//  private SampleProvider lsColour;
//  private float[] lsData;
  /*
   * Here is where the odometer correction code should be run.
   */
  public OdometryCorrection() {
	    this.odo = odometer;
//	    this.lsSensor = new EV3ColorSensor(lsPort);
	    this.colorDetector = colorSensor.getMode("Red");
	    this.dataArray = new float[colorSensor.sampleSize()];
	  }
  
  public void run() {
    long correctionStart, correctionEnd;
//    
//    while (true) {
      correctionStart = System.currentTimeMillis();
      
      boolean lineDetected = false; 															//when the robot detects a line
      
      colorSensor.fetchSample(dataArray, 0);													//fetch sample, store in array dataArray
      float lightIntensity = dataArray[0];
      
      if(lightIntensity <= 100 && lightIntensity > 70 && !lineDetected) {												//when line detected
    	  theta = odo.getXYT()[2]*180/Math.PI;													//get theta reading
    	  
    	  if((theta >= 315 && theta < 360) || (theta >= 0 && theta < 45)) {						//NORTH
    		  counterY++;
    		  if(counterY == 1) {
    			  tempYCoordinate = 0;
    		  }
    		  if(counterY == 2) {
    			  tempYCoordinate = TILE_SIZE;
    		  }
    		  if(counterY == 3) {
    			  tempYCoordinate = 2*TILE_SIZE;
    		  }
    		  odo.setY(tempYCoordinate);
    	  }
    	  else if(theta >= 45 && theta < 135) {													//EAST
    		  counterX++;
    		  if(counterX == 1) {
    			  tempXCoordinate = 0;
    		  }
    		  if(counterX == 2) {
    			  tempXCoordinate = TILE_SIZE;
    		  }
    		  if(counterX == 3) {
    			  tempXCoordinate = 2*TILE_SIZE;
    		  }
    		  odo.setX(tempXCoordinate);
    	  }
    	  else if(theta >= 135 && theta < 225) {												//SOUTH
    		  counterY++;
    		  if(counterY == 4) {
    			  tempYCoordinate = 2*TILE_SIZE;
    		  }
    		  if(counterY == 5) {
    			  tempYCoordinate = TILE_SIZE;
    		  }
    		  if(counterY == 6) {
    			  tempYCoordinate = 0;
    		  }
    		  odo.setY(tempYCoordinate);
    	  }
    	  else if(theta >= 225 && theta < 315) {												//WEST
    		  counterX++;
    		  if(counterX == 4) {
    			  tempXCoordinate = 2*TILE_SIZE;
    		  }
    		  if(counterX == 5) {
    			  tempXCoordinate = TILE_SIZE;
    		  }
    		  if(counterX == 6) {
    			  tempXCoordinate = 0;
    		  }
    		  odo.setX(tempXCoordinate);
    	  }
    	  Sound.beep();
    	  lineDetected = true;
      }
      else {
    	  lineDetected = false;
      }

      // TODO Update odometer with new calculated (and more accurate) values, eg:
//     odometer.setXYT(0.3, 19.23, 5.0);

      // this ensures the odometry correction occurs only once every period
      correctionEnd = System.currentTimeMillis();
      if (correctionEnd - correctionStart < CORRECTION_PERIOD) {
        Main.sleepFor(CORRECTION_PERIOD - (correctionEnd - correctionStart));
      }
    }
  }
  
  
//}
