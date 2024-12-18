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
  //Serial.write("Hello");
  if (Serial.available() > 0) {
    Serial.println("Have input");
    String content = Serial.readStringUntil('\n');
    Serial.print(content+'\n');
    if(content.indexOf("buzzer") > -1) {
        Serial.println("Verb is buzzer");
        String action = content.substring(content.indexOf("|")+1,content.length());
        Serial.println(action);
        if(action.equals("ON")) {
          digitalWrite(buzzer,HIGH);
        } else {
            digitalWrite(buzzer,LOW);
        }
    } else {
       
    }
      
    
  }
		

  
  delay(500);
}

