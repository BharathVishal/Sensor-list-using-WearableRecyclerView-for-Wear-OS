/**
 *
 * Copyright 2019-2025 Bharath Vishal G.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 **/

package com.bharathvishal.sensorlistusingwearablerecyclerview.Utilities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import com.bharathvishal.sensorlistusingwearablerecyclerview.Constants.Constants
import java.util.ArrayList

object InfoUtilities {
    //Sensor details
    fun getSensorDetails(context: Context?): List<Sensor> {
        var resSensorList: MutableList<Sensor> = ArrayList()
        try {
            val smm = context?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
            resSensorList.addAll(smm.getSensorList(Sensor.TYPE_ALL))
        } catch (e: Exception) {
            resSensorList = ArrayList()
        }
        return resSensorList
    }


    private fun getAndroidWearType(context1: Context?): String {
        var resultString: String = Constants.ROUND_WATCH
        try {
            val tempBool: Boolean = context1?.resources?.configuration?.isScreenRound!!
            resultString = if (tempBool)
                Constants.ROUND_WATCH
            else
                Constants.SQUARE_WATCH
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return resultString
    }


    fun isWearableRound(context1: Context?): Boolean {
        try {
            val resultString: String =
                getAndroidWearType(
                    context1
                )
            return resultString == Constants.ROUND_WATCH
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
}