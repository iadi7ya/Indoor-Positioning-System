#define trigPin 11
#define echoPin 12
#define led 3
#define trigPinB 22
#define echoPinB 23

void setup() {
  Serial.begin (9600);
  pinMode(trigPin, OUTPUT);
  pinMode(echoPin, INPUT);
  pinMode(led, OUTPUT);
  pinMode(trigPinB, OUTPUT);
  pinMode(echoPinB, INPUT);
}

void loop() {
  long duration, distance;
  long durationB, distanceB;
  digitalWrite(trigPin, LOW);  // Added this line
  delayMicroseconds(2); // Added this line
  digitalWrite(trigPin, HIGH);
  //  delayMicroseconds(1000); - Removed this line
  delayMicroseconds(10); // Added this line
  digitalWrite(trigPin, LOW);
  duration = pulseIn(echoPin, HIGH);
  //distance calculation using ultrasonic signal for first sensor
  //labelOne
  distance = (duration / 2) / 29.1;

  digitalWrite(trigPinB, LOW);
  delayMicroseconds(2);
  digitalWrite(trigPinB, HIGH);
  delayMicroseconds(10);
  digitalWrite(trigPin, LOW);
  durationB = pulseIn(echoPinB, HIGH);
  //labelOneEnd
  //distance calculation using ultrasonic signal for second sensor
  distanceB = (durationB / 2) / 29.1;
  int recv = 0;
  if (distance <= 0 || distanceB <= 0) {
    Serial.println("-1 % -1");
  }

  else {
    //printing to com6
    Serial.print(distance);
    Serial.print(" % ");
    Serial.print(distanceB);
    Serial.println("");
  }

  if (Serial.available() > 0) {
    recv = Serial.read();
    // if 'y' (decimal 121) is received, turn LED/Powertail on
    // anything other than 121 is received, turn LED/Powertail off
    if (recv == 121) {
      digitalWrite(led, HIGH);
    } else {
      digitalWrite(led, LOW);
    }

    // confirm values received in serial monitor window
    //Serial.print("--Arduino received: ");
    //Serial.println(recv);
  }
  delay(15000);
}
