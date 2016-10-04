git clone https://github.com/intel-iot-devkit/mraa
cd mraa
mkdir build
cd build
cmake ..
make
sudo make install
sudo ldconfig /usr/local/lib/ 
