# dragonboardSensor
## Dragonboard Sensor Mezzanine
1. Add debain UNIX

[Install UNIX] (http://www.96boards.org/documentation/ConsumerEdition/DragonBoard-410c/Downloads/Debian.md/)
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
5. Create projects directory

   ```
   cd /
   mkdir projects
   chmod 777 ./projects
   ```
6. exit out of root

   ```
   exit
   ```
7. Change to projects directory

   ```
   cd /projects
   ```
8. cloan repository

   ```
   git clone https://github.com/dstrower/dragonboardSensor
   ```
9. change to scripts directory

   ```
   cd /projects/dragonboardSensor/scripts
   ```
10. Update Debain

   ```
   ./updateDebian.sh
   ```
11. Install extra tool packages

   ```
   ./installTools.sh
   ```
12. Install MRAA library

   ```
   ./installMRAA.sh
   ```
13. Install UPM library. Please be patient, this will take about 30 minutes.

   ```
   ./instalUPM.sh
   ```
14. Configure software.

   ```
   ./configureSoftware.sh
   ```
15. Reboot System to get the changes.

   ```
   sudo reboot
   ```
