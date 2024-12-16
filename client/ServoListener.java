package client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ServoListener implements ActionListener {

  ClientPanel parent;
  private  String servoAngle1 = "0";
  private  String servoAngle2 = "0";
  private  String servoAngle3 = "0";
  private ServoAngleListener servoAngleListener1;
  private ServoAngleListener servoAngleListener2;

  private ServoAngleListener servoAngleListener3;

  public ServoListener(ClientPanel cp) {
    this.parent = cp;
  }
  @Override
  public void actionPerformed(ActionEvent e) {
     System.out.println("Testing servos");
    JFrame frame = parent.getClientFrame();
    final JDialog modelDialog = createDialog(frame);
    modelDialog.setVisible(true);
  }

  private JDialog createDialog(final JFrame frame){
    final JDialog modelDialog = new JDialog(frame, "Servo Tester!",
        Dialog.ModalityType.DOCUMENT_MODAL);

    modelDialog.setBounds(132, 132, 300, 200);
    Container dialogContainer = modelDialog.getContentPane();
    dialogContainer.setLayout(new BorderLayout());

    JPanel panel1 = new JPanel();
    panel1.setLayout(new GridLayout(2, 1));
    //panel1.setLayout(new FlowLayout());
    JPanel textPanel = new JPanel();
    textPanel.setLayout(new GridLayout(3, 1));
    JPanel servoPanel1 = new JPanel();
    servoPanel1.setLayout(new GridLayout(1,2));
    JLabel servo1 = new JLabel("Servo1 Angle: ");
    JTextField servoInput1  = new JTextField(getServoAngle1(), 20);
    servoAngleListener1 = new ServoAngleListener(servoInput1, getServoAngle1());
    servoInput1.getDocument().addDocumentListener(servoAngleListener1);
    servoPanel1.add(servo1,BorderLayout.EAST);
    servoPanel1.add(servoInput1,BorderLayout.WEST);
    textPanel.add(servoPanel1);
    //Servo 2
    JPanel servoPanel2 = new JPanel();
    servoPanel2.setLayout(new GridLayout(1,2));
    JLabel servo2 = new JLabel("Servo2 Angle: ");
    JTextField servoInput2  = new JTextField(getServoAngle2(), 20);
    servoAngleListener2 = new ServoAngleListener(servoInput2, getServoAngle2());
    servoInput2.getDocument().addDocumentListener(servoAngleListener2);
    servoPanel2.add(servo2,BorderLayout.EAST);
    servoPanel2.add(servoInput2,BorderLayout.WEST);
    textPanel.add(servoPanel2);
    //Servo 3
    JPanel servoPanel3 = new JPanel();
    servoPanel3.setLayout(new GridLayout(1,2));
    JLabel servo3 = new JLabel("Servo3 Angle: ");
    JTextField servoInput3  = new JTextField(getServoAngle3(), 20);
    servoAngleListener3 = new ServoAngleListener(servoInput3, getServoAngle3());
    servoInput3.getDocument().addDocumentListener(servoAngleListener3);
    servoPanel3.add(servo3,BorderLayout.EAST);
    servoPanel3.add(servoInput3,BorderLayout.WEST);
    textPanel.add(servoPanel3);
    //Button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(2,1));
    MoveServoButtonListener moveServoButtonListener = new MoveServoButtonListener(this);
    JButton servoButton = addButton("Move Servos",moveServoButtonListener,buttonPanel,true);
    JButton okButton = new JButton("Done");

    okButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        modelDialog.setVisible(false);
      }
    });
    panel1.add(textPanel,BorderLayout.NORTH);
    buttonPanel.add(servoButton, BorderLayout.NORTH);
    buttonPanel.add(okButton, BorderLayout.SOUTH);
    panel1.add(buttonPanel,BorderLayout.SOUTH);
    dialogContainer.add(panel1, BorderLayout.SOUTH);

    return modelDialog;
  }

  private JButton addButton(String label, ActionListener actionListener, JPanel subPanel, boolean enabled) {
    JButton button = new JButton(label);
    button.setEnabled(enabled);
    button.addActionListener(actionListener);
    subPanel.add(button);
    return button;
  }


  public Socket getSocket() {
    return parent.getSocket();
  }

  public  String getServoAngle1() {
    return servoAngle1;
  }

  public  String getServoAngle2() {
    return servoAngle2;
  }

  public  String getServoAngle3() {
    return servoAngle3;
  }

  public ServoAngleListener getServoAngleListener1() {
    return servoAngleListener1;
  }

  public ServoAngleListener getServoAngleListener2() {
    return servoAngleListener2;
  }

  public ServoAngleListener getServoAngleListener3() {
    return servoAngleListener3;
  }
}
