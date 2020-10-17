# Indoor-Positioning-System
<br>
An indoor positioning system (IPS) is a system to locate objects or people inside a closed environmet or a building using radio waves, magnetic fields, acoustic signals, or other sensory information collected by mobile devices.  
<br><br>
Global navigation satellite systems (GPS or GNSS) are generally not suitable to establish indoor locations, since microwaves will be attenuated and scattered by roofs, walls and other objects. However, in order to make positioning signals ubiquitous, integration between GPS and indoor positioning can be made.  
<br><br>
My solution for Indoor Positioning system is based on "Time of arrival" method.  For this I have used Ultrasonic sensor(HC-SR04) connected with arduino atmega 2560 (and a cardboard for placing a object on it to determine location of this object) as they cost very less and gives very precise information (values). this hardware setup is connected with iot-platform(take your own platform and change the code accordingly; i.e ; url and sensor observation links) , in this way location of object placed on cardboard can be seen remotely from anywhere in the world as I have used web interface for showing the location.
<br><br>
There is possibility of making it so much advance by adding some additional feature and additional hardware. One of the most important feature that i want to add is that we can also able see the 'orientation' of object if moving along with the direction.
