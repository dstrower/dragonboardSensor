package client;

import javax.swing.JFrame;

public class ClientFrame extends JFrame {

  public ClientFrame() {
    setTitle("DragonBoard 410c");
    setSize(440,80);
    ClientPanel panel = new ClientPanel();
    add(panel);
    pack();
  }

}
