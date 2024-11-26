import serial
import time

ser = serial.Serial('/dev/ttyACM0', 9600)  # Replace with your Arduino's port 

def set_servo_angle(angle):
    ser.write(str(angle).encode() + b'\n')

try:
    while True:
        angle = int(input("Enter servo angle (0-180): "))
        set_servo_angle(angle)
        time.sleep(0.1)

except KeyboardInterrupt:
    ser.close()
