package com.tail.node.Utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.content.SharedPreferences
import android.location.Location
import android.os.Build
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.tail.node.R
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStreamWriter
import java.lang.Exception
import java.util.*

class LocationService : Service() {
    var isServiceStarted = false
    private val NOTIFICATION_ID = "Location_Notification"
    var mLocation :Location? = null

    override fun onCreate() {
        super.onCreate()
        isServiceStarted = true
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, NOTIFICATION_ID)
                .setOngoing(false)
                .setSmallIcon(R.drawable.ic_launcher_background)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager: NotificationManager =
                getSystemService(NOTIFICATION_SERVICE) as NotificationManager
            val notificationChannel = NotificationChannel(
                NOTIFICATION_ID,
                NOTIFICATION_ID, NotificationManager.IMPORTANCE_LOW
            )
            notificationChannel.description = NOTIFICATION_ID
            notificationChannel.setSound(null, null)
            notificationManager.createNotificationChannel(notificationChannel)
            startForeground(1, builder.build())
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val timer = Timer()
        LocationHelper().startListeningUserLocation(
            this, object : MyLocationListener {
                override fun onLocationChanged(location: Location?) {
                    location?.latitude
                    location?.longitude
                    mLocation = location
                    mLocation?.let {
//                        Log.d("Location", "Latitude ${it.latitude} , Longitude ${it.longitude}")
//                        Toast.makeText(applicationContext, "Latitude ${it.latitude} , Longitude ${it.longitude}", Toast.LENGTH_LONG).show()
                        val sharedPreferences: SharedPreferences = applicationContext.getSharedPreferences("LatLong", MODE_PRIVATE)
                        val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                        editor.putString("lat",it.latitude.toString())
                        editor.putString("long",it.longitude.toString())
                        editor.apply()
                        editor.commit()
                    }
                }
            })

        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}
