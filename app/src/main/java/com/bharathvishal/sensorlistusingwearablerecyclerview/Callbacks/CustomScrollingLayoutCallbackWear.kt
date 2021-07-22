package com.bharathvishal.sensorlistusingwearablerecyclerview.Callbacks

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableLinearLayoutManager
import kotlin.math.abs
import kotlin.math.min

private const val MAX_ICON_PROGRESS = 0.65f

class CustomScrollingLayoutCallbackWear : WearableLinearLayoutManager.LayoutCallback() {

    private var progressToCenter: Float = 0f

    override fun onLayoutFinished(child: View, parent: RecyclerView) {
        child.apply {
            // Figure out % progress from top to bottom
            val centerOffset = (height.toFloat() / 2.0f) / parent.height.toFloat()
            val yRelativeToCenterOffset = y / parent.height + centerOffset

            // Normalize for center
            progressToCenter = abs(0.5f - yRelativeToCenterOffset)
            // Adjust to the maximum scale
            progressToCenter = min(
                progressToCenter,
                MAX_ICON_PROGRESS
            )

            scaleX = 1 - progressToCenter
            scaleY = 1 - progressToCenter
        }
    }
}