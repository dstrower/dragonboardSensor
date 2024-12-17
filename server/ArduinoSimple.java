package server;

public class ArduinoSimple implements Arduino {


  @Override
  public void sendMessage(String message) {
     System.out.println("Sending: " + message + " to the Arduino");
  }
}
