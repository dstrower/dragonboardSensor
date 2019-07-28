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
7. Add a fix before updating debain.

   ```
   sudo apt-mark hold linux-image-4.14.0-qcomlt-arm64
   ```
  [To see why this step is needed] (https://discuss.96boards.org/t/no-network-devices-available-after-updating-debian/4954/4)
   
8. Start following instructions starting on step 6.

  [Steps (Start on 6)] (https://github.com/96boards/Sensor_Mezzanine_Getting_Started/blob/master/README.md)
  

  

   
