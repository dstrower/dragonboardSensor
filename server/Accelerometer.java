package server;
import java.util.AbstractList;
import upm_lsm6ds3h.*;
public interface Accelerometer {
  public floatVector getAccelerometer();
  public void update();
  public floatVector getGyroscope();
  public String getTemperature();
  public String getTemperature(boolean flag);

}
