import com.fazecast.jSerialComm.*;

import java.io.OutputStream;
import java.util.Scanner;

public class ServoTest {
    public static void main(String[] args) {
        // Open the serial port
        SerialPort comPort = SerialPort.getCommPort("/dev/tty96B0"); // Replace with your port name
        comPort.openPort();

        // Set up the port parameters
        comPort.setBaudRate(9600);
        comPort.setNumDataBits(8);
        comPort.setNumStopBits(SerialPort.ONE_STOP_BIT);
        comPort.setParity(SerialPort.NO_PARITY);
        Scanner scanner = new Scanner(System.in);
        // Write to the serial port
        try {
            OutputStream outputStream = comPort.getOutputStream();
            while(true) {
		System.out.println("Enter angle for servo 1 (quit to quit)");
                String number = scanner.nextLine().trim();
                if("quit".equals(number)) {
                  break; 
                }
		System.out.println("Enter angle for servo 2");
                String number2 = scanner.nextLine().trim();
		String message = number + "| " + number2;
		System.out.println("Sending message: "+message);
                outputStream.write(message.getBytes());
                outputStream.write(System.getProperty("line.separator").getBytes());
                outputStream.flush();

           }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Close the serial port
        comPort.closePort();
    }
}


