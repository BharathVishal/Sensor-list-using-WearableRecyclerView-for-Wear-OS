package com.bharathvishal.sensorlistusingwearablerecyclerview.Activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import android.support.wearable.input.RotaryEncoder
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.widget.NestedScrollView
import androidx.wear.ambient.AmbientModeSupport
import com.bharathvishal.wearablerecyclerviewsample.R
import com.bharathvishal.sensorlistusingwearablerecyclerview.Fragments.SensorListFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity(), AmbientModeSupport.AmbientCallbackProvider {
    val ambientLogTag = "Ambient"
    private lateinit var ambientController: AmbientModeSupport.AmbientController
    private var activityContextMain: Context? = null

    @Suppress("DEPRECATION")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            activityContextMain = this@MainActivity
            ambientController = AmbientModeSupport.attach(this@MainActivity)

            try {
                contentMainScroll?.setOnGenericMotionListener(View.OnGenericMotionListener { v, ev ->
                    if (ev.action == MotionEvent.ACTION_SCROLL && RotaryEncoder.isFromRotaryEncoder(
                            ev
                        )
                    ) {
                        // Don't forget the negation here
                        val delta = -RotaryEncoder.getRotaryAxisValue(ev) *
                                RotaryEncoder.getScaledScrollFactor(activityContextMain)

                        //Log.d("scroll1", "invoked this " + delta.roundToInt())
                        // Swap these axes if you want to do horizontal scrolling instead
                        contentMainScroll?.scrollBy(0, delta.roundToInt())

                        return@OnGenericMotionListener true
                    }
                    false
                })
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
                val burnIn = ambientDetails?.getBoolean(WearableActivity.EXTRA_BURN_IN_PROTECTION)
                val lowBit = ambientDetails?.getBoolean(WearableActivity.EXTRA_LOWBIT_AMBIENT)
                Log.d(ambientLogTag, "burnIn protection : $burnIn")
                Log.d(ambientLogTag, "lowBit : $lowBit")
                Log.d(ambientLogTag, "isAmbient : " + ambientController?.isAmbient)
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
