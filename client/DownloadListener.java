package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class DownloadListener implements ActionListener {

  ClientPanel parent;

  public DownloadListener(ClientPanel cp) {
    this.parent = cp;
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    FileDownloader fileDownloader = new FileDownloader(parent);
    Thread thread = new Thread(fileDownloader);
    thread.start();
  }
}
