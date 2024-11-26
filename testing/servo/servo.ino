#include <Servo.h>

Servo myservo;

void setup() {
  myservo.attach(4); // Attach servo to pin 9
  Serial.begin(9600); // Initialize serial communication
}

void loop() {
  if (Serial.available() > 0) {
    int angle = Serial.parseInt(); // Read angle from serial port
    if(angle > 0) {
       myservo.write(angle); // Set servo position
    }
  }
  delay(1000);
}

