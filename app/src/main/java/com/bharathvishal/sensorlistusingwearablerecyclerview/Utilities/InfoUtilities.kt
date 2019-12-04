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