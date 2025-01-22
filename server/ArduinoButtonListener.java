package server;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ArduinoButtonListener {

  private Server server;

  public ArduinoButtonListener(Server s) {
    this.server = s;
  }


  private void displayMessage(String message) {
    server.displayMessage(message);
  }

  private void buzzer() throws IOException, InterruptedException {
    server.buzzer();
  }

  private void makeRecording() {
    server.makeRecording();
  }

  public void sendMessageToClient(String message) {
	if(message.contains("Button|On")) {
		displayMessage("Waiting 5 seconds");
		try {
		  TimeUnit.SECONDS.sleep(5);
		  displayMessage("Zeroing accelerometer.");
		  server.getRecorder().zeroAccelerometer();
		  buzzer();
		  //recorder.zeroAccelerometer();
		  makeRecording();
		  displayMessage("Completed recording.");
		} catch (InterruptedException e) {
		  throw new RuntimeException(e);
		} catch (IOException e) {
		  throw new RuntimeException(e);
		}
	}
	else {
    server.sendMessageToClient(message);
	}
  }

}
