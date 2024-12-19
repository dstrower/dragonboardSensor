package server;

public interface Arduino  {
  public void sendMessage(String message);
  public void setArduinoButtonListener(ArduinoButtonListener arduinoButtonListener);
}
