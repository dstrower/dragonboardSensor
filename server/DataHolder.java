package server;

import java.util.ArrayList;
import java.util.List;
import upm_lsm6ds3h.floatVector;

public class DataHolder {

  public DataHolder(float x, float y,float z) {
    this.xOffset = x;
    this.yOffset = y;
    this.zOffset = z;
  }

  private float xOffset;
  private float yOffset;
  private float zOffset;
  private List<Float> xAccelerationList = new ArrayList<>();
  private List<Float> yAccelerationList = new ArrayList<>();
  private List<Float> zAccelerationList = new ArrayList<>();
  private List<Float> xVelocityList = new ArrayList<>();
  private List<Float> yVelocityList = new ArrayList<>();
  private List<Float> zVelocityList = new ArrayList<>();
  private List<Float> xPositionList = new ArrayList<>();
  private List<Float> yPositionList = new ArrayList<>();
  private List<Float> zPositionList = new ArrayList<>();
  private List<Float> timeElapseList = new ArrayList<>();


  private float xAccelerationAverage = 0.0F;
  private float yAccelerationAverage = 0.0F;
  private float zAccelerationAverage = 0.0F;

  private float xVelocityCurrent = 0.0F;
  private float yVelocityCurrent = 0.0F;
  private float zVelocityCurrent = 0.0F;
  private float xVelocityAverage = 0.0F;
  private float yVelocityAverage = 0.0F;
  private float zVelocityAverage = 0.0F;
  private float xPosition = 0.0F;
  private float yPosition = 0.0F;
  private float zPosition = 0.0F;
  int count = 0;
  public void addPoint(float timeElapse, floatVector accelData, floatVector gyroData, float tempC, float tempF) {
    float xAcceleration = accelData.get(0) - xOffset;
    float yAcceleration = accelData.get(1) - yOffset;
    float zAcceleration = accelData.get(2) - zOffset;
    xAccelerationList.add(xAcceleration);
    yAccelerationList.add(yAcceleration);
    zAccelerationList.add(zAcceleration);
    timeElapseList.add(timeElapse);
    if(timeElapse >0) {
      xAccelerationAverage = (xAccelerationList.get(count) + xAccelerationList.get(count -1))/2.0F;
      yAccelerationAverage = (yAccelerationList.get(count) + yAccelerationList.get(count -1))/2.0F;
      zAccelerationAverage = (zAccelerationList.get(count) + zAccelerationList.get(count -1))/2.0F;
      xVelocityCurrent = xVelocityCurrent + xAccelerationAverage*timeElapse;
      yVelocityCurrent = yVelocityCurrent + yAccelerationAverage*timeElapse;
      zVelocityCurrent = zVelocityCurrent + zAccelerationAverage*timeElapse;
    }
    xVelocityList.add(xVelocityCurrent);
    yVelocityList.add(yVelocityCurrent);
    zVelocityList.add(zVelocityCurrent);
    if(timeElapse >0) {
      xVelocityAverage = (xVelocityList.get(count) + xVelocityList.get(count-1))/2.0F;
      yVelocityAverage = (yVelocityList.get(count) + yVelocityList.get(count-1))/2.0F;
      zVelocityAverage = (zVelocityList.get(count) + zVelocityList.get(count-1))/2.0F;
    }
    xPosition = xPosition + xVelocityAverage*timeElapse;
    yPosition = yPosition + yVelocityAverage*timeElapse;
    zPosition = zPosition + zVelocityAverage*timeElapse;
    xPositionList.add(xPosition);
    yPositionList.add(yPosition);
    zPositionList.add(zPosition);
    count++;
  }

}
