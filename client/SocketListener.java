package client;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

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
        System.out.println("From socket: " + line);
        parent.getTextArea().append(line+'\n');
      } catch (IOException e) {
        running = false;
      }
    }
  }

  public void stopThread() {
    running = false;
  }
}
