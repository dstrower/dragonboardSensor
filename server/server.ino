#include <Servo.h>

Servo myservo;
Servo myservoTwo;
const int buzzer = 7;
void setup() {
  pinMode(buzzer,OUTPUT);
  myservo.attach(4); // Attach servo to pin 4
  myservoTwo.attach(5); // Attach servo to pin 5
  Serial.begin(9600); // Initialize serial communication
}

void loop() {
  if (Serial.available() > 0) {
    String content = Serial.readStringUntil('\n');
    
          digitalWrite(buzzer,HIGH);
      
       
     
    
  }
		

  
  delay(500);
}

