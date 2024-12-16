package client;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ServoAngleListener implements DocumentListener {
  private JTextField display;
  private  String angle;
  public ServoAngleListener(JTextField jt,String angle) {
    this.display = jt;
    this.setAngle(angle);
  }

  @Override
  public void insertUpdate(DocumentEvent e) {
    setAngle(display.getText());
  }

  @Override
  public void removeUpdate(DocumentEvent e) {
    setAngle(display.getText());
  }

  @Override
  public void changedUpdate(DocumentEvent e) {
    setAngle(display.getText());
  }
  public void setAngle(String ang) {
    this.angle = ang;
  }

  public String getAngle() {
    return angle;
  }
}
