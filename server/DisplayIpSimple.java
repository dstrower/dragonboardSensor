package server;

public class DisplayIpSimple implements DisplayIp {
  @Override
  public void displayIp(String ip) {
    System.out.println("ip = "+ip);
  }
}
