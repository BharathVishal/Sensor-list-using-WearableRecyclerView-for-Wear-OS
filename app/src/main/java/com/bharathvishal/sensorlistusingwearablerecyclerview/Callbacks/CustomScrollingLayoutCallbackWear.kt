/**
 *
 * Copyright 2019-2023 Bharath Vishal G.
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