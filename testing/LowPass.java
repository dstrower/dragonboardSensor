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

public class LowPass
{
    public static void main(String[] args) throws InterruptedException
    {
// ! [Interesting]

        // Instantiate a LSM6DS3H instance using default i2c bus and address
        LSM6DS3H sensor = new LSM6DS3H();

        // For SPI, bus 0, you would pass -1 as the address, and a
        // valid pin for CS:
        // LSM6DS3H(0, -1, 10);
        float samples = 0;
	float total_x = 0;
	float total_y = 0;
	float total_z = 0;
        float ave_x = 0;
        float ave_y = 0;
        float ave_z = 0;
	float samples_needed = 100;
	float sample_rate = 100;
	float thousand = 1000;
	int display_rate = 1000;
	int last_display = 0;
	float theta_x = 0;
	float theta_y = 0;
	float theta_z = 0;
	boolean phase2 = false;
        while (true)
            {
                // update our values from the sensor
                sensor.update();

                floatVector data = sensor.getAccelerometer();

                //
		//System.out.println("Accelerometer x: " + data.get(0)
                                   //+ " y: " + data.get(1)
                                   //+ " z: " + data.get(2)
                                   //+ " g");

                data = sensor.getGyroscope();
                samples = samples + 1;
                float dx = data.get(0);
                float dy = data.get(1);
		float dz = data.get(2);
		dx = dx - ave_x;
		dy = dy - ave_y;
		dz = dz - ave_z;
		if(samples < samples_needed) 
		{
		   total_x = total_x + dx;
		   total_y = total_y + dy;
		   total_z = total_z + dz;
		   ave_x = total_x / samples;
		   ave_y = total_y / samples;
		   ave_z = total_z / samples;
		} else {
	                phase2 = true;
			theta_x = theta_x + dx * (sample_rate/thousand);
			theta_y = theta_y + dy * (sample_rate/thousand);
			theta_z = theta_z + dz * (sample_rate/thousand);
		}

            
                //
		//System.out.println("Compensation Temperature: "
                                   //
				   //+ sensor.getTemperature()
                                   //
				   //+ " C / "
                                   //
				   //+ sensor.getTemperature(true)
                                   //
				   //+ " F");

                Thread.sleep(100);
		last_display = last_display + (int)sample_rate;
		if(phase2 && last_display > display_rate) {
                    System.out.println("Phase II  Gyroscope x: " + dz
                                   + " y: " + dy
                                   + " z: " + dz
                                   + " dps");
		    System.out.println("theta_x: " + theta_x + " theta_y: " + theta_y + " theta_z: " + theta_z);
		    last_display = 0;
		}
            }

// ! [Interesting]
    }
}
