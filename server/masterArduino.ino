#include <Servo.h>

Servo myservo;
Servo myservoTwo;

void setup() {
  myservo.attach(4); // Attach servo to pin 4
  myservoTwo.attach(5); // Attach servo to pin 5
  Serial.begin(9600); // Initialize serial communication
}

void loop() {
  if (Serial.available() > 0) {
    String servoOne = Serial.readStringUntil('|');
    int angle = servoOne.toInt();
    String servoTwo = Serial.readStringUntil('\n');
    int angleTwo = servoTwo.toInt();
    if(angle > 0) {
       myservo.write(angle); // Set servo position
    }
    if(angleTwo > 0) {
       myservoTwo.write(angleTwo); // Set servo position
    }
  }
  delay(1000);
}

