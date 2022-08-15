package com.bharathvishal.sensorlistusingwearablerecyclerview.Activities

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.Sensor
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.wear.ambient.AmbientModeSupport
import androidx.wear.compose.material.ScalingLazyColumn
import androidx.wear.compose.material.items
import com.bharathvishal.sensorlistusingwearablerecyclerview.Utilities.InfoUtilities
import com.bharathvishal.sensorlistusingwearablerecyclerview.theme.Material3AppTheme
import com.bharathvishal.wearablerecyclerviewsample.R
import kotlinx.coroutines.*
import java.lang.ref.WeakReference

class MainActivityCompose : AppCompatActivity(), AmbientModeSupport.AmbientCallbackProvider,
    CoroutineScope by MainScope() {
    val ambientLogTag = "Ambient"
    private lateinit var ambientController: AmbientModeSupport.AmbientController
    private var activityContextMain: Context? = null

    private var progressVisibilityVal = mutableStateOf(true)
    private var lazyComposeViewVisibilityVal = mutableStateOf(false)
    private var noSensorsOnDeviceVisVal = mutableStateOf(false)

    private lateinit var sensorList: List<Sensor>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Material3AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color.Black
                ) {
                    MainViewImplementation()
                }
            }
        }

        try {
            activityContextMain = this
            ambientController = AmbientModeSupport.attach(this)

            sensorList = ArrayList()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        displayHideProgressBar(true)

        //Coroutine to get sensors
        getSensors(activityContextMain!!)
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

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @Composable
    fun MainViewImplementation() {
        Column {
            Box(
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                CardViewMain()
            }
        }
    }

    @Composable
    fun CardViewMain() {
        Column {
            Spacer(modifier = Modifier.padding(top = 1.dp))
            Card(
                modifier = Modifier
                    .padding(1.dp)
                    .fillMaxWidth()
                    .fillMaxHeight(),
                colors = CardDefaults.cardColors(
                    containerColor = Color.Black
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally,
                )
                {
                    LogoAndAppNameComposable()
                    CircularProgressAnimated(progressVisibilityVal.value)
                    NoSensorsOnWearableDeviceComposable(noSensorsOnDeviceVisVal.value)
                    ScalingLazyColumnComposable(lazyComposeViewVisibilityVal.value)
                }//end of column
            }//end of card
        }//end of outer column
    }//end of card view main

    @Composable
    fun LogoAndAppNameComposable() {
        Spacer(modifier = Modifier.padding(top = 10.dp))

        Image(
            painter = painterResource(R.drawable.sensor_info_frg),
            contentDescription = "Image Logo",
            modifier = Modifier
                .requiredHeight(50.dp)
                .requiredWidth(50.dp)
                .padding(1.dp)
        )

        Text(
            text = "SENSOR LIST",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            color = Color.White,
            style = MaterialTheme.typography.bodySmall
        )

        Divider(thickness = 0.5.dp, color = Color.White)
        Spacer(modifier = Modifier.padding(top = 4.dp))
    }

    @Composable
    fun ScalingLazyColumnComposable(visibilityState: Boolean) {
        if (visibilityState) {
            ScalingLazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                contentPadding = PaddingValues(1.dp),
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                if (sensorList != null) {
                    items(sensorList) { item ->
                        ComposableCardViewSensor(item.name, item.vendor)
                    }
                }
            }
        }
    }


    @Composable
    private fun CircularProgressAnimated(visibilityState: Boolean) {
        if (visibilityState) {
            CircularProgressIndicator(color = Color.White)
        }
    }

    @Composable
    private fun NoSensorsOnWearableDeviceComposable(visibilityState: Boolean) {
        if (visibilityState) {
            Text(
                text = "No sensors on your wearable device",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                color = Color.White,
                style = MaterialTheme.typography.labelMedium,
                maxLines = 1
            )
        }
    }

    @Composable
    fun ComposableCardViewSensor(
        sensorName: String,
        sensorVendor: String,
    ) {
        Card(
            modifier = Modifier
                .padding(1.dp, 1.dp, 1.dp, 1.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 1.dp
            ),
            shape = RoundedCornerShape(8.dp)
        ) {

            Column(Modifier.padding(4.dp)) {
                Text(
                    text = sensorName,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White,
                    maxLines = 1
                )
                Text(
                    text = sensorVendor,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    style = MaterialTheme.typography.labelMedium,
                    maxLines = 1
                )
            }
        }
    }


    private fun displayHideProgressBar(show: Boolean?) {
        if (show == true) {
            progressVisibilityVal.value = true
            lazyComposeViewVisibilityVal.value = false
        } else {
            progressVisibilityVal.value = false
            lazyComposeViewVisibilityVal.value = true
        }
    }

    private fun getSensors(context: Context) {
        val contextRef: WeakReference<Context> = WeakReference(context)

        //Coroutine
        launch(Dispatchers.Default) {
            try {
                val contextInner = contextRef.get()

                if (contextInner != null) {
                    sensorList = InfoUtilities.getSensorDetails(contextInner).toMutableList()
                }

                //UI Thread
                withContext(Dispatchers.Main) {
                    noSensorsOnDeviceVisVal.value = sensorList?.size!! <= 0
                    displayHideProgressBar(false)
                }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
            }
        }
    }


    //Preview for jetpack composable view
    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        Material3AppTheme {
            MainViewImplementation()
            //ComposableCardViewSensor("App new", "com.com.com")
        }
    }
}
