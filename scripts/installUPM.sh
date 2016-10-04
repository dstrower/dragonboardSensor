sudo ln -s /usr/bin/swig3.0 /usr/bin/swig
git clone https://github.com/intel-iot-devkit/upm
cd upm
mkdir build
cd build
cmake -DBUILDSWIGNODE=OFF ..
make
sudo make install
sudo ldconfig /usr/local/lib/libupm-*
