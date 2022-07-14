package com.bharathvishal.sensorlistusingwearablerecyclerview.Activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.ViewConfiguration
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.InputDeviceCompat
import androidx.core.view.MotionEventCompat
import androidx.core.view.ViewConfigurationCompat
import androidx.wear.ambient.AmbientModeSupport
import com.bharathvishal.sensorlistusingwearablerecyclerview.Fragments.SensorListFragment
import com.bharathvishal.wearablerecyclerviewsample.R
import com.bharathvishal.wearablerecyclerviewsample.databinding.ActivityMainBinding
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), AmbientModeSupport.AmbientCallbackProvider {
    val ambientLogTag = "Ambient"
    private lateinit var ambientController: AmbientModeSupport.AmbientController
    private var activityContextMain: Context? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        try {
            activityContextMain = this@MainActivity
            ambientController = AmbientModeSupport.attach(this@MainActivity)

            try {
                binding.contentMainScroll.setOnGenericMotionListener { v, ev ->
                    if (ev.action == MotionEvent.ACTION_SCROLL &&
                        ev.isFromSource(InputDeviceCompat.SOURCE_ROTARY_ENCODER)
                    ) {
                        val delta = -ev.getAxisValue(MotionEventCompat.AXIS_SCROLL) *
                                ViewConfigurationCompat.getScaledVerticalScrollFactor(
                                    ViewConfiguration.get(activityContextMain as MainActivity), activityContextMain as MainActivity
                                )

                        binding.contentMainScroll.scrollBy(0, delta.roundToInt())
                        true
                    } else {
                        false
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            try {
                val mgr = supportFragmentManager
                val trans = mgr.beginTransaction()
                trans.replace(
                    R.id.mainActivityFragment,
                    SensorListFragment()
                )
                trans.disallowAddToBackStack()
                trans.commit()
            } catch (e: Exception) {
                Log.d("ExceptionHandled", "exception handled in fragment begin trans")
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getAmbientCallback(): AmbientModeSupport.AmbientCallback {
        return AmbientCallback()
    }


    private inner class AmbientCallback : AmbientModeSupport.AmbientCallback() {
        override fun onEnterAmbient(ambientDetails: Bundle?) {
            super.onEnterAmbient(ambientDetails)
            try {
                Log.d(ambientLogTag, "Ambient mode entered")
                Log.d(ambientLogTag, "isAmbient : " + ambientController.isAmbient)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }


        override fun onExitAmbient() {
            super.onExitAmbient()
            try {
                Log.d(ambientLogTag, "Ambient mode exited")
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}
