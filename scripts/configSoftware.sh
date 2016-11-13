sudo adduser linaro i2c # Allow the normal user to perform i2c operations
git clone https://github.com/96boards/96boards-tools
sudo cp 96boards-tools/70-96boards-common.rules /etc/udev/rules.d/
sudo cp 96boards-sensors.sh /etc/profile.d
sudo cp /etc/profile.d/96boards-sensors.sh /etc/X11/Xsession.d/96boardssensors
