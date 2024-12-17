package server;

import com.fazecast.jSerialComm.*;

import java.io.OutputStream;
import java.util.Scanner;

public class ArduinoSerial implements Arduino {

  public void sendMessage(String message) {
	  System.out.println("Sending " + message + " to Arduino");
    // Open the serial port
    SerialPort comPort = SerialPort.getCommPort("/dev/tty96B0"); // Replace with your port name
    comPort.openPort();

    // Set up the port parameters
    comPort.setBaudRate(9600);
    comPort.setNumDataBits(8);
    comPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
    comPort.setParity(SerialPort.NO_PARITY);
    // Write to the serial port
    try {
      OutputStream outputStream = comPort.getOutputStream();
      outputStream.write(message.getBytes());
      outputStream.write(System.getProperty("line.separator").getBytes());
      outputStream.flush();
    } catch (Exception e) {
      e.printStackTrace();
    }
    // Close the serial port
    comPort.closePort();
  }
}

