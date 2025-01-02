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

package com.bharathvishal.sensorlistusingwearablerecyclerview.Adapters

/*
  Created by Bharath Vishal
 */

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bharathvishal.wearablerecyclerviewsample.R

class SensorAdapter(private val context1: Context, private val stringList: MutableList<Sensor>) :
    RecyclerView.Adapter<SensorAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var cardView: CardView
        var textViewSensorName: TextView
        var textViewsensorVendor: TextView

        init {
            Log.d("sensor1","init block")
            cardView = view.findViewById(R.id.card_view_sensor)
            textViewSensorName = view.findViewById(R.id.sensor_Name_Text)
            textViewsensorVendor = view.findViewById(R.id.sensor_Vendor_Text)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view2 =
            LayoutInflater.from(context1).inflate(R.layout.sensor_card_layout, parent, false)
        return ViewHolder(
            view2
        )
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val sensorName = stringList[position].name
        val sensorVendor = stringList[position].vendor

        viewHolder.textViewSensorName.text = sensorName
        viewHolder.textViewsensorVendor.text = "Vendor : $sensorVendor"
    }

    override fun getItemCount(): Int {
        return stringList.size
    }
}