package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ShutdownListener implements ActionListener {

  ClientPanel parent;

  public ShutdownListener(ClientPanel cp) {
    this.parent = cp;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Socket socket = parent.getSocket();
    DataOutputStream out = null;
    try {
      out = new DataOutputStream(
          socket.getOutputStream());
      out.writeUTF("shutdown");
    } catch (IOException i) {
      System.out.println(i);
      return;
    }
    parent.disconnectionMade();
  }
}
