sudo adduser linaro i2c # Allow the normal user to perform i2c operations
git clone https://github.com/96boards/96boards-tools
sudo cp 96boards-tools/70-96boards-common.rules /etc/udev/rules.d/
cat | sudo tee /etc/profile.d/96boards-sensors.sh << EOF
export JAVA_TOOL_OPTIONS="-Dgnu.io.rxtx.SerialPorts=/dev/tty96B0"
export MONITOR_PORT=/dev/tty96B0
export PYTHONPATH="$PYTHONPATH:/usr/local/lib/python2.7/site-packages"
EOF
sudo cp /etc/profile.d/96boards-sensors.sh /etc/X11/Xsession.d/96boardssensors
