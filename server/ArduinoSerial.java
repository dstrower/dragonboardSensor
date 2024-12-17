package server;

import com.fazecast.jSerialComm.*;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;

public class ArduinoSerial implements Arduino, Runnable {
  SerialPort comPort = SerialPort.getCommPort("/dev/tty96B0"); // Replace with your port name

  private DataInputStream in = null;
  private volatile boolean running = true;
  public ArduinoSerial() {
    comPort.openPort();

    // Set up the port parameters
    comPort.setBaudRate(9600);
    comPort.setNumDataBits(8);
    comPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
    comPort.setParity(SerialPort.NO_PARITY);
  }

  public void sendMessage(String message) {
	  System.out.println("Sending " + message + " to Arduino");
    // Open the serial port


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

  }

  @Override
  public void run() {
    System.out.println("Starting Arduino reader");
    in = new DataInputStream(
        new BufferedInputStream(comPort.getInputStream()));
    while (running) {
      try {
        int size = in.available();
        if(size > 0) {
          System.out.println("Message heard");
          byte[] b = new byte[100];
          in.read(b,0,size);
          String line = new String(b);
          System.out.println("From Arduino: " + line);
        }

      } catch (IOException e) {
        running = false;
      }
    }
  }
}

