package server;

import java.io.IOException;
import java.util.Properties;
import java.io.BufferedReader;
import java.io.FileReader;

public class StartServer {

  private void runApp() {
    Properties properties = getProperties();
    String ipAddressFile = properties.getProperty("ipaddressFile");
    String displayIpClass =  properties.getProperty("displayIpClass");
    String ipAddress = getIpaddress(ipAddressFile);
    System.out.println("ipaddres is: "+ ipAddress);
    displayIp(displayIpClass,ipAddress);
    Server server = new Server(5000,properties);
  }

  private void displayIp(String displayIpClass,String ip) {
    try {
      Object displayer = Class.forName(displayIpClass).newInstance();
      DisplayIp displayIP = (DisplayIp) displayer;
      displayIP.displayIp(ip);
    } catch (InstantiationException e) {
      throw new RuntimeException(e);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    } catch (ClassNotFoundException e) {
      throw new RuntimeException(e);
    }
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

  private Properties getProperties() {
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
