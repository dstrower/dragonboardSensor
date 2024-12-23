package client;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ClientPanel extends JPanel {

  private JPanel panel;
  private JTextField display;
  private String ipAddress = "192.10.0.1";
  private IPAddressListener ipAddressListener;
  private JButton connectButton;
  private JButton downloadButton;
  private JButton disconnectButton;
  private JButton testBuzzerButton;
  private JButton testServosButton;
  private JButton testAccelButton;
  private JButton zeroAccelButton;
  private JButton createRecordingButton;
  private float xOffset = 0.0F;
  private JButton uploadButton;
  private JButton turnOffDragonBoard;
  private JButton stopDragonBoardServer;
  private Socket socket = null;
  SocketListener socketListener = null;
  Thread thread = null;
  private JPanel textPanel;


  private JTextArea textArea;
  private JTextArea accelTextArea;
  private ClientFrame clientFrame;

  public ClientPanel(ClientFrame cf) {
    setLayout(new BorderLayout());
    this.clientFrame =  cf;
    JPanel ipAddressPanel = new JPanel();
    ipAddressPanel.setLayout(new GridLayout(1, 2));
    display = new JTextField(ipAddress, 20);
    ipAddressListener = new IPAddressListener(display, ipAddress);
    display.getDocument().addDocumentListener(getIpAddressListener());
    JLabel ipLabel = new JLabel("DragonBoard Ip: ");
    ipAddressPanel.add(ipLabel, BorderLayout.EAST);
    ipAddressPanel.add(display, BorderLayout.WEST);
    panel = new JPanel();
    panel.setLayout(new GridLayout(3, 1));
    panel.add(ipAddressPanel, BorderLayout.NORTH);
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(11,1));
    ConnectListener connectListener = new ConnectListener(this);
    DisconnectListener disconnectListener = new DisconnectListener(this);
    ShutdownListener shutdownListener = new ShutdownListener(this);
    DownloadListener downloadListener = new DownloadListener(this);
    UploadListener uploadListener = new UploadListener(this);
    downloadButton = addButton("Download Files", downloadListener, buttonPanel, true);
    connectButton = addButton("Connect To DragonBoard", connectListener, buttonPanel, true);
    disconnectButton = addButton("Disconnect From DragonBoard", disconnectListener, buttonPanel, false);
    uploadButton = addButton("Upload Files", uploadListener, buttonPanel, false);
    TestBuzzerListener testBuzzerListener = new TestBuzzerListener(this);
    testBuzzerButton = addButton("Test Buzzer", testBuzzerListener, buttonPanel, false);
    ServoListener servoListener = new ServoListener(this);
    testServosButton = addButton("Test Servos",servoListener,buttonPanel,false);
    TestAccelListener testAccelListener = new TestAccelListener(this);
    accelTextArea = testAccelListener.getTextArea();
    testAccelButton = addButton("Test Accelerometer", testAccelListener, buttonPanel, false);
    ZeroAccelListener zeroAccelListener = new ZeroAccelListener(this);
    zeroAccelButton = addButton("Zero Accelerometer", zeroAccelListener, buttonPanel, false);
    RecordAccelListener recordAccelListener = new RecordAccelListener(this);
    createRecordingButton = addButton("Make Recording", recordAccelListener, buttonPanel, false);
    turnOffDragonBoard = addButton("Turn off DragonBoard", shutdownListener, buttonPanel, false);
    StopServerListener stopServerListener = new StopServerListener(this);
    stopDragonBoardServer = addButton("Stop DragonBoard Server", stopServerListener, buttonPanel, false);
    panel.add(buttonPanel, BorderLayout.CENTER);
    textPanel = new JPanel();
    textArea = new JTextArea(8,40);
    textPanel.add(textArea);
    panel.add(textPanel, BorderLayout.SOUTH);
    add(panel);
  }

  public ClientFrame getClientFrame() {
     return this.clientFrame;
  }
  public void makeConnection(String ipAddress, int port) throws IOException {
    socket = new Socket(ipAddress, port);
  }

  public void connectionMade() {
    connectButton.setEnabled(false);
    uploadButton.setEnabled(true);
    disconnectButton.setEnabled(true);
    testBuzzerButton.setEnabled(true);
    testServosButton.setEnabled(true);
    testAccelButton.setEnabled(true);
    zeroAccelButton.setEnabled(true);
    createRecordingButton.setEnabled(true);
    turnOffDragonBoard.setEnabled(true);
    stopDragonBoardServer.setEnabled(true);
    socketListener = new SocketListener(this);
    thread = new Thread(socketListener);
    thread.start();
  }

  public void disconnectionMade() {
    socketListener.stopThread();
    connectButton.setEnabled(true);
    uploadButton.setEnabled(false);
    disconnectButton.setEnabled(false);
    testBuzzerButton.setEnabled(false);
    testServosButton.setEnabled(false);
    testAccelButton.setEnabled(false);
    createRecordingButton.setEnabled(false);
    zeroAccelButton.setEnabled(false);
    turnOffDragonBoard.setEnabled(false);
    stopDragonBoardServer.setEnabled(false);
  }

  private JButton addButton(String label, ActionListener actionListener, JPanel subPanel, boolean enabled) {
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

  public JTextArea getTextArea() {
    return textArea;
  }

  public JTextArea getAccelTextArea() {
    return accelTextArea;
  }
}
