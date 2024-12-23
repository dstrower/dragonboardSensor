package server;

// A Java program for a Server

import java.net.*;
import java.io.File;
import java.io.*;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import upm_lsm6ds3h.*;


public class Server {

  //initialize socket and input stream
  private Socket socket = null;
  private ServerSocket server = null;
  private DataOutputStream out = null;
  private DataInputStream in = null;
  private static final String OVER = "Over";
  private static final String SHUTDOWN = "shutdown";
  private static final String STOP_SERVER = "stopServer";
  private static final String BUZZER = "buzzer";
  private static final String SERVO = "servo";
  private static final String UPLOAD = "upload";
  private static final String RECORDER_TEST = "recorder_test";
  private static final String RECORD = "record";
  private static final String ZERO = "zero";
  private FileUploader fileUploader = new FileUploader();
  private Recorder recorder = null;

  public void sendMessageToClient(String message) {
    try {
      out.writeUTF(message);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public Server(int port, StartServer parent) {

  }

  // constructor with port
  public void start(int port, StartServer parent) {
    LSM6DS3H accelerometer = parent.getAccelerometer();
    String uploadDirectory = parent.getUploadDirectory();
    System.out.println("uploadDirectory = " + uploadDirectory);
    File myFile = new File(uploadDirectory + "accel.csv");
    try {
    myFile.createNewFile();
    } catch(Exception e) {
    }
    recorder = new Recorder(accelerometer, this, myFile);
    boolean loopInProcess = true;
    Properties properties = parent.getProperties();
    String shutdownFile = properties.getProperty("shutdownFile");
    // starts server and waits for a connection
    try {
      server = new ServerSocket(port);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    while (loopInProcess) {
      try {

        System.out.println("Server started");

        System.out.println("Waiting for a client ...");

        socket = server.accept();
        System.out.println("Client accepted");

        // takes input from the client socket
        in = new DataInputStream(
            new BufferedInputStream(socket.getInputStream()));
        out = new DataOutputStream(
            socket.getOutputStream());
        //String line = in.readUTF();

        // reads message from client until "Over" is sent
        while (true) {
          try {
            String line = in.readUTF();
            System.out.println(line);

            if (OVER.equals(line)) {
              break;
            } else if (BUZZER.equals(line)) {
              out.writeUTF("Hitting buzzer");
              parent.sendToArduino("buzzer|ON");
              TimeUnit.SECONDS.sleep(1);
              parent.sendToArduino("buzzer|OFF");
            } else if (SHUTDOWN.equals(line)) {
              parent.displayMessage("Shutting down");
              createFile(shutdownFile);
              loopInProcess = false;
              break;
            } else if (STOP_SERVER.equals(line)) {
              parent.displayMessage("Server stopped.");
              loopInProcess = false;
              break;
            } else if (line != null && line.contains(SERVO)) {
              parent.displayMessage("Moving servos");
              parent.sendToArduino(line);
            } else if (UPLOAD.equals(line)) {
              Thread thread = new Thread(fileUploader);
              thread.start();
            } else if (RECORDER_TEST.equals(line)) {
              recorder.setRecord(false);
              Thread thread = new Thread(recorder);
              thread.start();
            } else if (RECORD.equals(line)) {
              recorder.setRecord(true);
              Thread thread = new Thread(recorder);
              thread.start();
            } else if (ZERO.equals(line)) {
              sendMessageToClient("Zeroing accelerometer.");
              recorder.zeroAccelerometer();
              sendMessageToClient("Completed Zeroing accelerometer.");
            }
          } catch (IOException i) {
            System.out.println(i);
            break;
          } catch (InterruptedException e) {
            throw new RuntimeException(e);
          }
        }
        System.out.println("Closing connection");

        // close connection
        socket.close();
        in.close();
      } catch (IOException i) {
        System.out.println(i);
      }
    }
    System.exit(0);
  }

  private void createFile(String shutdownFile) {
    File shutDown = new File(shutdownFile);
    try {
      shutDown.createNewFile();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
