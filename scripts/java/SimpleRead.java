import java.io.*;
import java.util.*;
import javax.comm.*;

public class SimpleRead {
    static CommPortIdentifier portId;
    static Enumeration portList;

    InputStream inputStream;
    SerialPort serialPort;
    Thread readThread;

    public static void main(String[] args) {
            portList = CommPortIdentifier.getPortIdentifiers();
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
               String name = portId.getName();
               System.out.println(name);
            }
    }
}

