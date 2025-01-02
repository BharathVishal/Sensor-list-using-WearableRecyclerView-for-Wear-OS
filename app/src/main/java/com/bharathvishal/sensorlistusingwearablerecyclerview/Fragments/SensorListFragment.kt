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

package com.bharathvishal.sensorlistusingwearablerecyclerview.Fragments

import android.content.Context
import android.hardware.Sensor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.wear.widget.WearableLinearLayoutManager
import com.bharathvishal.sensorlistusingwearablerecyclerview.Adapters.SensorAdapter
import com.bharathvishal.sensorlistusingwearablerecyclerview.Callbacks.CustomScrollingLayoutCallbackWear
import com.bharathvishal.sensorlistusingwearablerecyclerview.Utilities.InfoUtilities
import com.bharathvishal.wearablerecyclerviewsample.databinding.FragmentSensorInfoBinding
import kotlinx.coroutines.*
import java.lang.ref.WeakReference
import java.util.*

class SensorListFragment : Fragment(), CoroutineScope by MainScope() {
    private var sensorList: List<Sensor>? = null
    private var adapter: SensorAdapter? = null
    private var wearablerecyclerViewLayoutManager: WearableLinearLayoutManager? = null

    private var _binding: FragmentSensorInfoBinding? = null
    private val binding get() = _binding!!

    private var activityContext: Context? = null
    private var customScrollingLayoutCallback: CustomScrollingLayoutCallbackWear? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSensorInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sensorList = ArrayList()

        if (isAdded) {
            activityContext = activity
            customScrollingLayoutCallback =
                CustomScrollingLayoutCallbackWear()

            wearablerecyclerViewLayoutManager = if (InfoUtilities.isWearableRound(activityContext))
                WearableLinearLayoutManager(activityContext, customScrollingLayoutCallback)
            else
                WearableLinearLayoutManager(activityContext)

            displayHideProgressBar(true)

            //Async
            getSensors(activityContext!!)
        }
    }


    private fun displayHideProgressBar(show: Boolean?) {
        if (show == true) {
            binding.sensorinfoProgressLLayout.visibility = View.VISIBLE
            binding.sensorinfoProgressSpinner.visibility = View.VISIBLE
            binding.sensorListRecyclerView.visibility = View.INVISIBLE
        } else {
            binding.sensorinfoProgressLLayout.visibility = View.GONE
            binding.sensorinfoProgressSpinner.visibility = View.GONE
            binding.sensorListRecyclerView.visibility = View.VISIBLE
        }
    }


    private fun getSensors(context: Context) {
        val contextRef: WeakReference<Context> = WeakReference(context)

        //Coroutine
        launch(Dispatchers.Default) {
            try {
                val contextInner = contextRef.get()

                if (contextInner != null) {
                    sensorList = InfoUtilities.getSensorDetails(contextInner)
                    adapter = if (sensorList != null) {
                        val tempItemList1 = ArrayList<Sensor>()
                        tempItemList1.addAll(sensorList!!)
                        SensorAdapter(
                            contextInner,
                            tempItemList1
                        )
                    } else {
                        val tempItemList = ArrayList<Sensor>()
                        SensorAdapter(
                            contextInner,
                            tempItemList
                        )
                    }
                }

                //UI Thread
                withContext(Dispatchers.Main) {
                    binding.sensorListRecyclerView.layoutManager = wearablerecyclerViewLayoutManager
                    binding.sensorListRecyclerView.setHasFixedSize(true)

                    if (adapter?.itemCount!! > 0) {
                        binding.sensorListRecyclerView.adapter = adapter
                    } else {
                        binding.noSensorsTextView.visibility = View.VISIBLE
                        binding.sensorListRecyclerView.visibility = View.GONE
                    }
                    displayHideProgressBar(false)

                    binding.sensorListRecyclerView.requestFocus()
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
