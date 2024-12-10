package client;

import java.io.*;
import java.net.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class ConnectListener implements ActionListener {

  String ipAddress;
  IPAddressListener ipAddressListener;

  ClientPanel parent;
  public ConnectListener(ClientPanel cp) {
    this.parent = cp;
    ipAddressListener = cp.getIpAddressListener();
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    ipAddress = ipAddressListener.getIpAddress();
    System.out.println("Connecting to DragonBoard at: " + ipAddress);


    // establish a connection
    try {
      parent.makeConnection(ipAddress, 5000);
      System.out.println("Connected");
      parent.connectionMade();
    } catch (UnknownHostException u) {
      System.out.println(u);
      return;
    } catch (IOException i) {
      System.out.println(i);
      return;
    }
  }
}
