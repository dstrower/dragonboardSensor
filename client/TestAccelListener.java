package client;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TestAccelListener implements ActionListener {

  ClientPanel parent;
  private JTextArea textArea =  new JTextArea(8,40);

  public TestAccelListener(ClientPanel cp) {
    this.parent = cp;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Socket socket = parent.getSocket();
    DataOutputStream out = null;
    try {
      out = new DataOutputStream(
          socket.getOutputStream());
      out.writeUTF("recorder_test");
    } catch (IOException i) {
      System.out.println(i);
      return;
    }
    textArea.setText("");
    JFrame frame = parent.getClientFrame();
    final JDialog modelDialog = createDialog(frame);
    modelDialog.setVisible(true);
  }

  private JDialog createDialog(final JFrame frame) {
    final JDialog modelDialog = new JDialog(frame, "Acceleromter Tester!",
        Dialog.ModalityType.DOCUMENT_MODAL);
    modelDialog.setBounds(50, 50, 300, 500);
    Container dialogContainer = modelDialog.getContentPane();
    dialogContainer.setLayout(new GridLayout(2, 1));
    JPanel textPanel = new JPanel();
    textPanel.add(textArea);
    //Button panel
    JPanel buttonPanel = new JPanel();
    buttonPanel.setLayout(new GridLayout(1,1));
    JButton okButton = new JButton("Done");

    okButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        modelDialog.setVisible(false);
      }
    });
    buttonPanel.add(okButton);
    dialogContainer.add(buttonPanel,BorderLayout.SOUTH);
    dialogContainer.add(textPanel,BorderLayout.NORTH);

    return modelDialog;
  }

  public JTextArea getTextArea() {
    return textArea;
  }
}
