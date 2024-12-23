package client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

public class SocketListener implements Runnable {

  private ClientPanel parent;
  private DataInputStream in = null;

  public SocketListener(ClientPanel cp) {
    this.parent = cp;
  }

  private volatile boolean running = true;

  @Override
  public void run() {
    Socket socket = parent.getSocket();

    try {
      in = new DataInputStream(
          new BufferedInputStream(socket.getInputStream()));
    } catch (IOException ex) {
      throw new RuntimeException(ex);
    }
    while (running) {
      try {
        String line = in.readUTF();
        if(!line.contains("accel")) {
          System.out.println("From socket: " + line);
          parent.getTextArea().append(line+'\n');
        } else {
          line = formatLine(line);
          parent.getAccelTextArea().append(line + '\n');
        }

      } catch (IOException e) {
        running = false;
      }
    }
  }

  private String formatLine(String line) {
    StringTokenizer st = new StringTokenizer(line,"|");
    String firstPart = st.nextToken();
    String secondPart = st.nextToken();
    StringTokenizer accelToken = new StringTokenizer(secondPart,",");
    String time = accelToken.nextToken();
    String x = accelToken.nextToken();
    String y = accelToken.nextToken();
    String z = accelToken.nextToken();
    String formattedLine =  x + " " + y +" " +  z;
    return formattedLine;
  }

  public void stopThread() {
    running = false;
  }
}
