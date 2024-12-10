package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import java.io.*;
import java.net.*;
public class TestBuzzerListener implements ActionListener {
  ClientPanel parent;
  public TestBuzzerListener(ClientPanel cp) {
     this.parent = cp;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Socket socket = parent.getSocket();
    DataOutputStream out = null;
    try {
      out = new DataOutputStream(
          socket.getOutputStream());
      out.writeUTF("buzzer");
    } catch (IOException i) {
    System.out.println(i);
    return;
  }
  }
}
