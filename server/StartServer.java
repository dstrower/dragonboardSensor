package server;

import java.io.IOException;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.FileReader;

public class StartServer {

  private  Properties properties;
  private Display display;

  private Arduino arduino;




  private void runApp() {
    try {
      properties = getProperties();
      String ipAddressFile = getProperties().getProperty("ipaddressFile");
      String displayClass = getProperties().getProperty("displayIpClass");
      String arduinoClass = getProperties().getProperty("arduinoClass");
      String ipAddress = getIpaddress(ipAddressFile);
      System.out.println("ipaddres is: " + ipAddress);
      Object displayer = Class.forName(displayClass).newInstance();
      display = (Display) displayer;
      Object ard = Class.forName(arduinoClass).newInstance();
      arduino  = (Arduino) ard;


      Server server = new Server(5000, this);
      ArduinoButtonListener arduinoButtonListener = new ArduinoButtonListener(server);
      arduino.setArduinoButtonListener(arduinoButtonListener);
      if(ard instanceof Runnable) {
        Thread thread = new Thread((Runnable) ard);
        thread.start();
      }
      displayMessage(ipAddress);
      server.start(5000, this);

    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
  }

  public void displayMessage(String message) {
    display.displayMessage(message);
  }

  public void sendToArduino(String message) {
    arduino.sendMessage(message);
  }

  private String getIpaddress(String ipAddressFile) {
     String ipAddress = null;
    BufferedReader reader;

    try {
      reader = new BufferedReader(new FileReader(ipAddressFile));
      String line = reader.readLine();

      while (line != null) {
        //System.out.println(line);
        // read next line
        if(line.contains(".")) {
          ipAddress = line;
        }
        line = reader.readLine();
      }

      reader.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
     return ipAddress;
  }

  public Properties getProperties() {
    Properties prop = new Properties();
    try {
      prop.load(Server.class.getClassLoader().getResourceAsStream("dragon.properties"));
    } catch (IOException e) {
      System.out.println("Exception: "+e);
    }
    return prop;
  }

  public static void main(String[] args) {
    StartServer myApp = new StartServer();
    myApp.runApp();
  }

}
