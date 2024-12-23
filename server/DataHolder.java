package server;

import java.util.ArrayList;
import java.util.HashMap;
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

  private List<Float> xAngleVelocityList = new ArrayList<>();
  private List<Float> yAngleVelocityList = new ArrayList<>();
  private List<Float> zAngleVelocityList = new ArrayList<>();
  private List<Float> xVelocityList = new ArrayList<>();
  private List<Float> yVelocityList = new ArrayList<>();
  private List<Float> zVelocityList = new ArrayList<>();
  private List<Float> xPositionList = new ArrayList<>();
  private List<Float> yPositionList = new ArrayList<>();
  private List<Float> zPositionList = new ArrayList<>();
  private List<Float> xAngleList = new ArrayList<>();
  private List<Float> yAngleList = new ArrayList<>();
  private List<Float> zAngleList = new ArrayList<>();
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
  private float xAngleVelocityAverage = 0.0F;
  private float yAngleVelocityAverage = 0.0F;
  private float zAngleVelocityAverage = 0.0F;
  private float xPosition = 0.0F;
  private float yPosition = 0.0F;
  private float zPosition = 0.0F;
  provate float deltaTime = 0.0F;
  private float xAngle = 0.0F;
  private float yAngle = 0.0F;
  private float zAngle = 0.0F;

  private float g = 32.2F;
  private float THOUSAND = 1000F;

  int count = 0;
  public void addPoint(float timeElapse, floatVector accelData, floatVector gyroData, float tempC, float tempF) {
    timeElapse = timeElapse/THOUSAND; //Turns it into seconds
    float xAcceleration = accelData.get(0) - xOffset;
    float yAcceleration = accelData.get(1) - yOffset;
    float zAcceleration = accelData.get(2) - zOffset;
    float xAngleVelocity = 0.0F;
    float yAngleVelocity = 0.0F;
    float zAngleVelocity = 0.0F;
    xAngleVelocityList.add(xAngleVelocity);
    yAngleVelocityList.add(yAngleVelocity);
    zAngleVelocityList.add(zAngleVelocity);
    xAccelerationList.add(xAcceleration);
    yAccelerationList.add(yAcceleration);
    zAccelerationList.add(zAcceleration);
    timeElapseList.add(timeElapse);
    if(timeElapse >0) {
	  deltaTime = timeElapse.get(count) - timeElapse(count -1);
      xAccelerationAverage = (xAccelerationList.get(count) + xAccelerationList.get(count -1))/2.0F;
      yAccelerationAverage = (yAccelerationList.get(count) + yAccelerationList.get(count -1))/2.0F;
      zAccelerationAverage = (zAccelerationList.get(count) + zAccelerationList.get(count -1))/2.0F;
      xAngleVelocityAverage = (xAccelerationList.get(count) + xAccelerationList.get(count-1))/2.0F;
      yAngleVelocityAverage = (yAccelerationList.get(count) + yAccelerationList.get(count-1))/2.0F;
      zAngleVelocityAverage = (zAccelerationList.get(count) + zAccelerationList.get(count-1))/2.0F;
      xVelocityCurrent = xVelocityCurrent + g*xAccelerationAverage*deltaTime;
      yVelocityCurrent = yVelocityCurrent + g*yAccelerationAverage*deltaTime;
      zVelocityCurrent = zVelocityCurrent + g*zAccelerationAverage*deltaTime;
    }
    xVelocityList.add(xVelocityCurrent);
    yVelocityList.add(yVelocityCurrent);
    zVelocityList.add(zVelocityCurrent);
    if(timeElapse >0) {
      xVelocityAverage = (xVelocityList.get(count) + xVelocityList.get(count-1))/2.0F;
      yVelocityAverage = (yVelocityList.get(count) + yVelocityList.get(count-1))/2.0F;
      zVelocityAverage = (zVelocityList.get(count) + zVelocityList.get(count-1))/2.0F;
    }
    xPosition = xPosition + xVelocityAverage*deltaTime;
    yPosition = yPosition + yVelocityAverage*deltaTime;
    zPosition = zPosition + zVelocityAverage*deltaTime;
    xPositionList.add(xPosition);
    yPositionList.add(yPosition);
    zPositionList.add(zPosition);
    xAngle = xAngle + xAngleVelocityAverage*deltaTime;
    yAngle = yAngle + yAngleVelocityAverage*deltaTime;
    zAngle = zAngle + zAngleVelocityAverage*deltaTime;
    count++;
  }

  public List<String> createDataList() {
    List<String> dataList = new ArrayList<>();
    List<String> headerList = new ArrayList<>();
    HashMap<String,List<Float>> valueMap = new HashMap<>();
    addNext("Time Elapsed (seconds)",timeElapse,headerList,valueMap);
    addNext("xAcceleration (g)",xAccelerationList,headerList,valueMap);
    addNext("yAcceleration (g)",yAccelerationList,headerList,valueMap);
    addNext("zAcceleration (g)",zAccelerationList,headerList,valueMap);
    addNext("xVelocity (ft/s)",xVelocityList,headerList,valueMap);
    addNext("yVelocity (ft/s)",yVelocityList,headerList,valueMap);
    addNext("zVelocity (ft/s)",zVelocityList,headerList,valueMap);
    addNext("xPosition (ft)",xPositionList,headerList,valueMap);
    addNext("yPosition (ft)",yPositionList,headerList,valueMap);
    addNext("zPosition (ft)",zPositionList,headerList,valueMap);
    String line = null;
    for(int i =0; i < headerList.size();i++) {
      String column = headerList.get(i);
      if(line == null) {
         line = column;
      } else {
         line = line + "," + column;
      }
    }
    dataList.add(line);
    for(int row=0; row < xAccelerationList.size(); row++) {
      line = null;
      for(int column =0; column < headerList.size();column++) {
         String headerName = headerList.get(column);
         List<Float> columnList = valueMap.get(headerName);
         String data = Float.toString(columnList.get(row));
         if(line == null) {
            line = data;
         } else {
            line = line + "," + data;
         }
      }
      dataList.add(line);
    }
    return dataList;
  }

  private void addNext(String description, List<Float> arrayList, List<String> headerList, HashMap<String, List<Float>> valueMap) {
    headerList.add(description);
    valueMap.put(description,arrayList);
  }
}
