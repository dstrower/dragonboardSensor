package server;

public class ArduinoSimple implements Arduino, Runnable {


  @Override
  public void sendMessage(String message) {
     System.out.println("Sending: " + message + " to the Arduino");
  }

  @Override
  public void run() {
     System.out.println("Arduino listener starting");
  }
}
