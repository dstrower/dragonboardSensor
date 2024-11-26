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
import java.lang.Math.*;

public class AccelAngle
{
    public static void main(String[] args) throws InterruptedException
    {
// ! [Interesting]

        // Instantiate a LSM6DS3H instance using default i2c bus and address
        LSM6DS3H sensor = new LSM6DS3H();

        // For SPI, bus 0, you would pass -1 as the address, and a
        // valid pin for CS:
        // LSM6DS3H(0, -1, 10);

        while (true)
            {
                // update our values from the sensor
                sensor.update();

                floatVector data = sensor.getAccelerometer();
		float acc_x =  data.get(0);
		float acc_y =  data.get(1);
		float acc_z =  data.get(2);
		acc_x = normalizeAngle(acc_x);
		acc_y = normalizeAngle(acc_y);
		acc_z = normalizeAngle(acc_z);
                double theta_x = Math.asin(acc_x);
                double theta_y = Math.asin(acc_y);
                double theta_z = Math.asin(acc_z);
		theta_x = Math.toDegrees(theta_x);
		theta_y = Math.toDegrees(theta_y);
		theta_z = Math.toDegrees(theta_z);

                //
		System.out.println("Angles: " + theta_x +", " + theta_y + ", " + theta_z);

                Thread.sleep(100);
            }

// ! [Interesting]
    }

    private static float normalizeAngle(float theta) { 
	    theta = Math.max(-1,theta);
	    theta = Math.min(1,theta);
	    return theta;
    }
}
