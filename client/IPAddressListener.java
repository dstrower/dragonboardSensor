package client;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class IPAddressListener implements DocumentListener {
  private JTextField display;
  private  String ipAddress;
  public IPAddressListener(JTextField jt,String ip) {
    this.display = jt;
    this.setIpAddress(ip);
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    setIpAddress(display.getText());
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    setIpAddress(display.getText());
  }

  @Override
  public void changedUpdate(DocumentEvent e) {
    setIpAddress(display.getText());
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }
}
