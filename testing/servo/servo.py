import RPi.GPIO as GPIO
import time

# Set GPIO numbering mode
GPIO.setmode(GPIO.BCM)

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

try:
    while True:
        # Set the servo to 90 degrees
        set_angle(90)
        time.sleep(1)

        # Set the servo to 0 degrees
        set_angle(0)
        time.sleep(1)

except KeyboardInterrupt:
    # Clean up the GPIO pins on exit
    pwm.stop()
    GPIO.cleanup()
