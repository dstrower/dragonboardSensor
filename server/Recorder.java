package server;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.AbstractList;

import upm_lsm6ds3h.*;
public class Recorder implements Runnable{

  private Accelerometer sensor;
  private Server server;
  private boolean record = true;
  private int sleepTime = 1000;

  private long sessionLength = 10;
  public Recorder(Accelerometer a,Server s) {
    this.sensor = a;
    this.server = s;
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
       String tempC = sensor.getTemperature();
       String tempF = sensor.getTemperature(true);
       sendDatatoClient(timeElapse,accelData,gyroData,tempC,tempF);
       count++;
       try {
         Thread.sleep(sleepTime);
       } catch (InterruptedException e) {
         throw new RuntimeException(e);
       }
     }
  }

  private void sendDatatoClient(long timeElapse, floatVector accelData, floatVector gyroData, String tempC, String tempF) {
    String line = "time=" + timeElapse;
    line = line + "," + "accelX=" + accelData.get(0);
    line = line + "," + "accely=" + accelData.get(1);
    line = line + "," + "accelz=" + accelData.get(2);
    line = line + "," + "gyroX=" + gyroData.get(0);
    line = line + "," + "gyroY=" + gyroData.get(1);
    line = line + "," + "gyroZ=" + gyroData.get(2);
    line = line + "," + "tempC=" + tempC;
    line = line + "," + "tempF=" + tempF;
    System.out.println(line);
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
