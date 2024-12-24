package server;

import java.io.File;

public class CreateNewFile {

  private String directory;

  public CreateNewFile(String dir) {
    this.directory = dir;
  }


  public File createFile() {
    File direct = new File(directory);
    File[] fileList = direct.listFiles();
    String filePattern = "accel_";
    int number = 0;
    int maxNumber = 0;
    for (int i = 0; i < fileList.length; i++) {
      File oneFile = fileList[i];
      String name = oneFile.getName();
      //System.out.println(name);
      if (name.contains(filePattern)) {
        int location = name.indexOf(filePattern);
        String rest = name.substring(location + filePattern.length(), name.length());
        location = rest.indexOf(".");
        String numberString = rest.substring(0, location);
        //System.out.println(numberString);
        number = Integer.parseInt(numberString);
        //System.out.println(number);
        if (number > maxNumber) {
          maxNumber = number;
        }
      }
    }
    //System.out.println("Max number = " + maxNumber);
    int newNumber = maxNumber + 1;
    //System.out.println("new number = " + newNumber);
    String numberString = Integer.toString(newNumber).trim();
    String zeroFilled = "0000";
    zeroFilled = zeroFilled.substring(0,4-numberString.length()) + numberString;
    //System.out.println(zeroFilled);
    String fileName = directory + filePattern + zeroFilled + ".csv";
    return new File(fileName);
  }


}
