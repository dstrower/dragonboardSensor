package server;

public class ArduinoButtonListener {

  private Server server;

  public ArduinoButtonListener(Server s) {
    this.server = s;
  }

  public void sendMessageToClient(String message) {
     server.sendMessageToClient(message);
  }

}
