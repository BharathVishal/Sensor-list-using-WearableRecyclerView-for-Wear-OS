package com.bharathvishal.sensorlistusingwearablerecyclerview.Fragments

import android.content.Context
import android.hardware.Sensor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.wear.widget.WearableLinearLayoutManager
import com.bharathvishal.sensorlistusingwearablerecyclerview.Activities.MainActivity
import com.bharathvishal.sensorlistusingwearablerecyclerview.Callbacks.CustomScrollingLayoutCallbackWear
import com.bharathvishal.wearablerecyclerviewsample.R
import com.bharathvishal.sensorlistusingwearablerecyclerview.Adapters.SensorAdapter
import com.bharathvishal.sensorlistusingwearablerecyclerview.Utilities.InfoUtilities
import kotlinx.android.synthetic.main.fragment_sensor_info.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.util.*

class SensorListFragment : Fragment() {
    private var sensorList: List<Sensor>? = null
    private var adapter: SensorAdapter? = null
    private var wearablerecyclerViewLayoutManager: WearableLinearLayoutManager? = null

    private var activityContext: Context? = null
    var customScrollingLayoutCallback: CustomScrollingLayoutCallbackWear? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_sensor_info, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sensorList = ArrayList()

        if (isAdded) {
            activityContext = activity as Context?
            customScrollingLayoutCallback =
                CustomScrollingLayoutCallbackWear()

            wearablerecyclerViewLayoutManager = if (InfoUtilities.isWearableRound(activityContext))
                WearableLinearLayoutManager(activityContext, customScrollingLayoutCallback)
            else
                WearableLinearLayoutManager(activityContext)

            displayHideProgressBar(true)

            //Async
            getSensors(activityContext as MainActivity)
        }
    }


    private fun displayHideProgressBar(show: Boolean?) {
        if (show == true) {
            sensorinfoProgressLLayout?.visibility = View.VISIBLE
            sensorinfoProgressSpinner?.visibility = View.VISIBLE
            sensor_List_RecyclerView?.visibility = View.INVISIBLE
        } else {
            sensorinfoProgressLLayout?.visibility = View.GONE
            sensorinfoProgressSpinner?.visibility = View.GONE
            sensor_List_RecyclerView?.visibility = View.VISIBLE
        }
    }


    private fun getSensors(context: Context) {
        doAsync {
            if (context != null) {
                sensorList = InfoUtilities.getSensorDetails(context)
                adapter = if (sensorList != null) {
                    val tempItemList1 = ArrayList<Sensor>()
                    tempItemList1.addAll(sensorList!!)
                    SensorAdapter(
                        context,
                        tempItemList1
                    )
                } else {
                    val tempItemList = ArrayList<Sensor>()
                    SensorAdapter(
                        context,
                        tempItemList
                    )
                }
            }

            uiThread {
                sensor_List_RecyclerView?.layoutManager = wearablerecyclerViewLayoutManager
                sensor_List_RecyclerView?.setHasFixedSize(true)

                if (adapter?.itemCount!! > 0) {
                    sensor_List_RecyclerView?.adapter = adapter
                } else {
                    no_sensors_textView?.visibility = View.VISIBLE
                    sensor_List_RecyclerView?.visibility = View.GONE
                }
                displayHideProgressBar(false)

                sensor_List_RecyclerView?.requestFocus()
            }
        }
    }
}
