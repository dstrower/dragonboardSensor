package server;

import upm_lsm6ds3h.floatVector;

public class AccelSimple implements Accelerometer{

  @Override
  public floatVector getAccelerometer() {
    return getFloatVector();
  }

  @Override
  public void update() {

  }

  @Override
  public floatVector getGyroscope() {
    return getFloatVector();
  }

  private static floatVector getFloatVector() {
    floatVector f = new floatVector();
    float x = 0.0f;
    float y = 0.0f;
    float z = 0.0f;
    f.add(x);
    f.add(y);
    f.add(y);
    return f;
  }

  @Override
  public String getTemperature() {
    return "0";
  }

  @Override
  public String getTemperature(boolean flag) {
    return "0";
  }
}
