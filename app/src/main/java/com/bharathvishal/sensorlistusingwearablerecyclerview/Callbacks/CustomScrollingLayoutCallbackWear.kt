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

package com.bharathvishal.sensorlistusingwearablerecyclerview.Callbacks

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableLinearLayoutManager.LayoutCallback

private const val MAX_ICON_PROGRESS = 0.65f

class CustomScrollingLayoutCallbackWear : LayoutCallback() {
    private var mProgressToCenter = 0f

    /*
     * Scales the item's icons and text the farther away they are from center allowing the main
     * item to be more readable to the user on small devices like Wear.
     */
    override fun onLayoutFinished(child: View, parent: RecyclerView) {

        // Figure out % progress from top to bottom.
        val centerOffset = child.height.toFloat() / 2.0f / parent.height.toFloat()
        val yRelativeToCenterOffset = child.y / parent.height + centerOffset

        // Normalizes for center.
        mProgressToCenter = Math.abs(0.5f - yRelativeToCenterOffset)

        // Adjusts to the maximum scale.
        mProgressToCenter = Math.min(mProgressToCenter, MAX_CHILD_SCALE)
        child.scaleX = 1 - mProgressToCenter
        child.scaleY = 1 - mProgressToCenter
    }

    companion object {
        // Max we scale the child View.
        private const val MAX_CHILD_SCALE = 0.65f
    }
}
