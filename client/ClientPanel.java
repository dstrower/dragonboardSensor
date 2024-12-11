package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ClientPanel extends JPanel {
  private JPanel panel;
  private JTextField display;
  private String ipAddress = "192.10.0.1";
  private IPAddressListener ipAddressListener;
  private JButton connectButton;
  private JButton disconnectButton;
  private JButton testBuzzerButton;
  private JButton turnOffDragonBoard;
  private JButton stopDragonBoardServer;
  private Socket socket = null;

   public ClientPanel() {
     setLayout(new BorderLayout());

     JPanel ipAddressPanel = new JPanel();
     ipAddressPanel.setLayout(new GridLayout(1,2));
     display = new JTextField(ipAddress,20);
     ipAddressListener = new IPAddressListener(display,ipAddress);
     display.getDocument().addDocumentListener(getIpAddressListener());
     JLabel ipLabel = new JLabel("DragonBoard Ip: ");
     ipAddressPanel.add(ipLabel,BorderLayout.EAST);
     ipAddressPanel.add(display,BorderLayout.WEST);
     panel = new JPanel();
     panel.setLayout(new GridLayout(2,1));
     panel.add(ipAddressPanel,BorderLayout.NORTH);
     JPanel buttonPanel = new JPanel();
     buttonPanel.setLayout(new GridLayout(5,1));
     ConnectListener connectListener = new ConnectListener(this);
     DisconnectListener disconnectListener = new DisconnectListener(this);
     ShutdownListener shutdownListener = new ShutdownListener(this);
     connectButton = addButton("Connect To DragonBoard",connectListener,buttonPanel,true);
     disconnectButton = addButton("Disconnect From DragonBoard",disconnectListener,buttonPanel,false);
     TestBuzzerListener testBuzzerListener = new TestBuzzerListener(this);
     testBuzzerButton = addButton("Test Buzzer",testBuzzerListener,buttonPanel,false);
     turnOffDragonBoard = addButton("Turn off DragonBoard",shutdownListener,buttonPanel,false);
     StopServerListener stopServerListener = new StopServerListener(this);
     stopDragonBoardServer = addButton("Stop DragonBoard Server",stopServerListener,buttonPanel,false);
     panel.add(buttonPanel,BorderLayout.SOUTH);
     add(panel);
   }

   public void makeConnection(String ipAddress, int port) throws IOException {
     socket = new Socket(ipAddress, port);
   }
   public void connectionMade() {
     connectButton.setEnabled(false);
     disconnectButton.setEnabled(true);
     testBuzzerButton.setEnabled(true);
     turnOffDragonBoard.setEnabled(true);
     stopDragonBoardServer.setEnabled(true);
   }

  public void disconnectionMade() {
    connectButton.setEnabled(true);
    disconnectButton.setEnabled(false);
    testBuzzerButton.setEnabled(false);
    turnOffDragonBoard.setEnabled(false);
    stopDragonBoardServer.setEnabled(false);
  }
  private JButton addButton(String label, ActionListener actionListener,JPanel subPanel,boolean enabled) {
     JButton button = new JButton(label);
     button.setEnabled(enabled);
     button.addActionListener(actionListener);
     subPanel.add(button);
     return button;
  }

  public IPAddressListener getIpAddressListener() {
    return ipAddressListener;
  }

  public Socket getSocket() {
    return socket;
  }
}