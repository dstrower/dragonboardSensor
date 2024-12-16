# dragonboardSensor
## Dragonboard Sensor Mezzanine
1. Add debain UNIX

  [Install UNIX] (https://www.96boards.org/documentation/consumer/dragonboard/dragonboard410c/installation/mac-sd.md.html)
  
2. Connect to network
3. Add root user

   ```
   sudo passwd root
   ```
   You will be promoted for new UNIX password. Write it down.
   Then execute
   ```
   sudo passwd -u root
   ```
4. sudo to root

   ```
   sudo -i
   ```
5. Make sure board has the correct time.

   ```
   ntpdate pool.ntp.org   
   ```
6. exit out of root

   ```
   exit
   ```
   
8. Start following instructions starting on step 6.

  [Steps (Start on 6)] (https://github.com/96boards/Sensor_Mezzanine_Getting_Started/blob/master/README.md)
  
  
9. Add java mraa package

```
sudo apt-get install libmraa-java
```
  
 10. Add java based upm package
 
 ```
sudo apt-get install libupm-java
```

  11. Decide where you want the code from the git repository to go. Change to that directory.
  
  12. Clone this repository.
   ```
git clone https://github.com/dstrower/dragonboardSensor
```
  
  

  

   