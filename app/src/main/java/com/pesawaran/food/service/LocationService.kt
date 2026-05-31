package com.pesawaran.food.service

import android.Manifest
import android.app.Service
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import androidx.core.app.ActivityCompat
import androidx.core.app.ServiceCompat
import com.google.android.gms.location.*
import com.pesawaran.food.utils.LogUtils
import kotlinx.coroutines.*

class LocationService : Service() {

    private val TAG = "LocationService"
    private var fusedLocationClient: FusedLocationProviderClient? = null
    private var locationCallback: LocationCallback? = null
    private val scope = CoroutineScope(Job() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        LogUtils.d(TAG, "Location service created")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startLocationUpdates()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        stopLocationUpdates()
        scope.cancel()
        LogUtils.d(TAG, "Location service destroyed")
    }

    private fun startLocationUpdates() {
        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000L)
            .setMinUpdateIntervalMillis(2000L)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                for (location in locationResult.locations) {
                    LogUtils.d(TAG, "Location: ${location.latitude}, ${location.longitude}")
                    handleLocationUpdate(location)
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                locationCallback!!,
                Looper.getMainLooper()
            )
            LogUtils.d(TAG, "Location updates started")
        }
    }

    private fun stopLocationUpdates() {
        locationCallback?.let {
            fusedLocationClient?.removeLocationUpdates(it)
        }
        LogUtils.d(TAG, "Location updates stopped")
    }

    private fun handleLocationUpdate(location: Location) {
        // Send location to server or repository
        scope.launch {
            try {
                // Update user location in repository
                LogUtils.d(TAG, "Location update handled")
            } catch (e: Exception) {
                LogUtils.e(TAG, "Error handling location update", e)
            }
        }
    }
}