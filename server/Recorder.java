package server;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.AbstractList;

import upm_lsm6ds3h.*;
public class Recorder implements Runnable{

  private LSM6DS3H sensor;
  private Server server;
  private boolean record = true;
  private int sleepTime = 1000;
  private float xOffset = 0.0F;
  private float yOffset = 0.0F;
  private float zOffset = 0.0F;

  private long sessionLength = 10000;
  public Recorder(LSM6DS3H a,Server s) {
    this.sensor = a;
    this.server = s;
  }
  
  private void resetOffsets() {
	   xOffset = 0.0F;
	   yOffset = 0.0F;
	   zOffset = 0.0F;
  }
  
  public void zeroAccelerometer() {
	  resetOffsets();
	  float count = 0.0F;
	  float timeElapse = 0.0F;
	  LocalTime startingTime = LocalTime.now();
	  float xTotal = 0.0F;
	  float yTotal = 0.0F;
	  float zTotal = 0.0F;


	  while(timeElapse  < sessionLength) {
		  if(count > 0) {
             LocalTime now = LocalTime.now();
             timeElapse = ChronoUnit.MILLIS.between(startingTime, now);
          }
		  sensor.update();
          floatVector accelData = sensor.getAccelerometer();
		  xTotal = xTotal + accelData.get(0);
		  yTotal = yTotal + accelData.get(1);
		  zTotal = zTotal + accelData.get(2);
		  count = count + 1;
		  try {
		     Thread.sleep(200);
                  } catch (InterruptedException e) {
                     throw new RuntimeException(e);
                  }
	  }
	  xOffset = xTotal/count;
	  yOffset = yTotal/count;
	  zOffset = zTotal/count;	  
  }


  @Override
  public void run() {
     LocalTime startingTime = LocalTime.now();
     int count = 0;
    long timeElapse = 0;
     while(timeElapse  < sessionLength) {
       if(count > 0) {
         LocalTime now = LocalTime.now();
         timeElapse = ChronoUnit.SECONDS.between(startingTime, now);
       }
       // update our values from the sensor
       sensor.update();
       floatVector accelData = sensor.getAccelerometer();
       floatVector gyroData = sensor.getGyroscope();
       String tempC = "0";
       String tempF = "32";
       sendDatatoClient(timeElapse,accelData,gyroData,tempC,tempF);
       count++;
       try {
         Thread.sleep(sleepTime);
       } catch (InterruptedException e) {
         throw new RuntimeException(e);
       }
     }
  }

  private void sendDatatoClient(float timeElapse, floatVector accelData, floatVector gyroData, String tempC, String tempF) {
    String line = "time=" + timeElapse;
    float x = accelData.get(0) - xOffset;
	float y = accelData.get(1) - yOffset;
	float z = accelData.get(2) - zOffset;
    line = line + "," + "accelX=" + x;
    line = line + "," + "accely=" + y;
    line = line + "," + "accelz=" + z;
    line = line + "," + "gyroX=" + gyroData.get(0);
    line = line + "," + "gyroY=" + gyroData.get(1);
    line = line + "," + "gyroZ=" + gyroData.get(2);
    line = line + "," + "tempC=" + tempC.toString();
    line = line + "," + "tempF=" + tempF.toString();
    line = line + "," + "gyroX=" + gyroData.get(0);
    line = line + "," + "gyroY=" + gyroData.get(1);
    line = line + "," + "gyroZ=" + gyroData.get(2);
    line = line + "," + "tempC=" + tempC.toString();
    line = line + "," + "tempF=" + tempF.toString();
    //System.out.println(line);
    server.sendMessageToClient("accel|"+line);
  }

  public boolean isRecord() {
    return record;
  }

  public void setRecord(boolean record) {
    this.record = record;
  }

  public int getSleepTime() {
    return sleepTime;
  }

  public void setSleepTime(int sleepTime) {
    this.sleepTime = sleepTime;
  }

  public long getSessionLength() {
    return sessionLength;
  }

  public void setSessionLength(long sessionLength) {
    this.sessionLength = sessionLength;
  }
}
