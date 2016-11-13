# dragonboardSensor
## Dragonboard Sensor Mezzanine
1. Add debain UNIX
2. Connect to network
3. Add root user

   ```
   sudo passwd root
   ```
   You will be promoted for new UNIX password. Write it down.
   Then execute
   ```
   sudo -passwd -u root
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
   git remote add origin git@https://github.com/dstrower/dragonboardSensor
   ```
9. change to scripts directory

   ```
   cd /projects/dragonboardSensor/scripts
   '''
10. Update Debain

   ```
   ./updateDebian.sh
   ```
