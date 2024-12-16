package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MoveServoButtonListener implements ActionListener {

  ServoListener parent;

  public MoveServoButtonListener(ServoListener sl) {
    this.parent = sl;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    String angle1 = parent.getServoAngleListener1().getAngle();
    String angle2 = parent.getServoAngleListener2().getAngle();
    String angle3 = parent.getServoAngleListener3().getAngle();
    System.out.println("Servo1 angle = " + angle1);
    System.out.println("Servo2 angle = " + angle2);
    System.out.println("Servo3 angle = " + angle3);
    String line = "servo|" + angle1 + "," + angle2 + "," + angle3;
    Socket socket = parent.getSocket();
    DataOutputStream out = null;
    try {
      out = new DataOutputStream(
          socket.getOutputStream());
      out.writeUTF(line);
    } catch (IOException i) {
      System.out.println(i);
    }
  }
}
