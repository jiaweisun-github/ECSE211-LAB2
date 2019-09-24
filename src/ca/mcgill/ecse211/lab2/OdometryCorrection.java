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
    long correctionStart, correctionEnd;//black: 0.14, white: 0.32
//    
    colorDetector.fetchSample(dataArray, 0);													//fetch sample, store in array dataArray
    float initialLightIntensity = dataArray[0];
    
    while (true) {
      correctionStart = System.currentTimeMillis();
      
      boolean lineDetected = false; 															//when the robot detects a line
      
      colorDetector.fetchSample(dataArray, 0);													//fetch sample, store in array dataArray
      System.out.println("           "+dataArray[0]);
      
      if((dataArray[0]/initialLightIntensity) < 0.7 && !lineDetected) {							//when line detected
    	  theta = odo.getXYT()[2]*180/Math.PI;													//get theta reading, convert to degrees
    	  Sound.playNote(Sound.FLUTE, 440, 250);
    	  
    	  //the (0,0) coordinate is at the right upper corner of the robot's position
    	  
    	  if (theta >= -45 && theta < 45) {						//NORTH, increment y-coordinate
    		  counterY++;	
    		  if(counterY == 1) {																	//1st line, set y-coordinate to TILE_SIZE (30.48)
    			  tempYCoordinate = TILE_SIZE;
    		  }
    		  if(counterY == 2) {																	//2nd line, set y-coordinate to 2*TILE_SIZE (61)
    			  tempYCoordinate = 2*TILE_SIZE;
    		  }
    		  if(counterY == 3) {																	//3nd line, set y-coordinate to 3*TILE_SIZE (61)
    			  tempYCoordinate = 3*TILE_SIZE;
    		  }
    		  odo.setY(tempYCoordinate);
    	  }
//    	  else if(theta >= 135 && theta < 225) {												//SOUTH
//    		  counterY++;
//    		  if(counterY == 4) {																	//4th line, set y-coordinate to 2*TILE_SIZE (61)
//    			  tempYCoordinate = 3*TILE_SIZE;
//    		  }
//    		  if(counterY == 5) {																	//5th line, set y-coordinate to TILE_SIZE (30.48)
//    			  tempYCoordinate = 2*TILE_SIZE;
//    		  }
//    		  if(counterY == 6) {																	//6th line, set y-coordinate to 0
//    			  tempYCoordinate = TILE_SIZE;
//    		  }
//    		  odo.setY(tempYCoordinate);
//    	  }
    	  else if(theta >= 45) {																//EAST
    		  counterX++;
    		  if(counterX == 1) {																	//1st line, set x-coordinate to TILE_SIZE (30.48)
    			  tempXCoordinate = TILE_SIZE;
    		  }
    		  if(counterX == 2) {																	//2nd line, set y-coordinate to 2*TILE_SIZE (61)
    			  tempXCoordinate = 2*TILE_SIZE;
    		  }
    		  if(counterX == 3) {																	//3rd line, set y-coordinate to 2*TILE_SIZE (61)
    			  tempXCoordinate = 3*TILE_SIZE;
    		  }
//    		 
    		  if(counterX == 4) {																	//4th line, set y-coordinate to 2*TILE_SIZE (61)
    			  tempXCoordinate = 3*TILE_SIZE;
    		  }
    		  if(counterX == 5) {																	//5th line, set y-coordinate to TILE_SIZE (30.48)
    			  tempXCoordinate = 2*TILE_SIZE;
    		  }
    		  if(counterX == 6) {																	//6th line, set y-coordinate to 0
    			  tempXCoordinate = TILE_SIZE;
    		  }
    		  odo.setX(tempXCoordinate);
    	  }
    	  
//    	  Sound.beep();
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
  
  
}
