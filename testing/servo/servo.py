import serial
import time

ser = serial.Serial('/dev/ttyACM0', 9600)  # Replace with your Arduino's port 

<<<<<<< HEAD
# Set the GPIO pin connected to the servo
servo_pin = 4

# Set the frequency of the PWM signal
frequency = 50

# Initialize the PWM pin
GPIO.setup(servo_pin, GPIO.OUT)
pwm = GPIO.PWM(servo_pin, frequency)

# Start the PWM with a duty cycle of 0 (servo at 0 degrees)
pwm.start(0)

def set_angle(angle):
    """Function to set the servo angle"""
    duty = angle/18 + 2
    GPIO.output(servo_pin, True)
    pwm.ChangeDutyCycle(duty)
    time.sleep(1)
    GPIO.output(servo_pin, False)
    pwm.ChangeDutyCycle(0)
=======
def set_servo_angle(angle):
    ser.write(str(angle).encode() + b'\n')
>>>>>>> 456d41238d24492e1253de00a53ecee54d797867

try:
    while True:
        angle = int(input("Enter servo angle (0-180): "))
        set_servo_angle(angle)
        time.sleep(0.1)

except KeyboardInterrupt:
    ser.close()
