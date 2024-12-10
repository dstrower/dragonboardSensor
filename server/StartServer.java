package server;

import java.io.IOException;
import java.util.Properties;

public class StartServer {

  private void runApp() {
    Properties properties = getProperties();
    Server server = new Server(5000,properties);
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
