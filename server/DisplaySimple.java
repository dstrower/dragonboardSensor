package server;

public class DisplaySimple implements Display {
  @Override
  public void displayMessage(String message) {
    System.out.println(message);
  }
}
