package com.bharathvishal.sensorlistusingwearablerecyclerview.Adapters

/*
  Created by Bharath Vishal
 */

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
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