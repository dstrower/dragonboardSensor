
/*
 * Author: Jon Trulson <jtrulson@ics.com>
 * Copyright (c) 2016-2017 Intel Corporation.
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 *
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

import java.util.AbstractList;

import upm_lsm6ds3h.*;

public class ShowAngle {
	public static void main(String[] args) throws InterruptedException {
		// ! [Interesting]
		long thousand = 1000;

		// Instantiate a LSM6DS3H instance using default i2c bus and address
		LSM6DS3H sensor = new LSM6DS3H();

		// For SPI, bus 0, you would pass -1 as the address, and a
		// valid pin for CS:
		// LSM6DS3H(0, -1, 10);
		float theta_x = 0;
		float theta_y = 0;
		float theta_z = 0;
		long lastTime = 0;
		while (true) {
			// update our values from the sensor
			sensor.update();

			floatVector data = sensor.getAccelerometer();

			data = sensor.getGyroscope();
			long currentTime = System.currentTimeMillis();
			float dx = data.get(0);
			float dy = data.get(1);
			float dz = data.get(2);
			if (lastTime > 0) {
				long deltaTime = (currentTime - lastTime)/thousand;
				theta_x = theta_x + dx*deltaTime;
				theta_y = theta_y + dy*deltaTime;
				theta_z = theta_z + dz*deltaTime;
			}
            lastTime = currentTime;
			System.out.println("Gyroscope x: " + theta_x + " y: " + theta_y + " z: " + theta_z + " degrees");

			Thread.sleep(100);
		}

		// ! [Interesting]
	}
}
