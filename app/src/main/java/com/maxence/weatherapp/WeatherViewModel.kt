package com.maxence.weatherapp

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import com.maxence.weatherapp.models.WeatherResult
import com.maxence.weatherapp.service.WeatherApiService
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

@SuppressLint("StaticFieldLeak")
class WeatherViewModel(private val activity: ComponentActivity) : ViewModel() {

    private val apiService = WeatherApiService()
    var weatherResults: WeatherResult by mutableStateOf(WeatherResult())
    var locationByGps: Location? = null
    // FusedLocationProviderClient - Main class for receiving location updates.
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    // LocationRequest - Requirements for the location updates, i.e.,
    // how often you should receive updates, the priority, etc.
    private lateinit var locationRequest: LocationRequest

    // LocationCallback - Called when FusedLocationProviderClient
    // has a new Location
    private lateinit var locationCallback: LocationCallback

    // This will store current location info
    private var currentLocation: Location? = null

    init {
        getCurrentLocation()
        getWeather()
    }

    private fun getWeather() {
        viewModelScope.launch {
            val weatherApiResult = apiService.getWeather("Londres")
            weatherResults = weatherApiResult

        }
    }

    private fun getCurrentLocation(): Location? {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        locationRequest = LocationRequest().apply {
            // Sets the desired interval for
            // active location updates.
            // This interval is inexact.
            interval = TimeUnit.SECONDS.toMillis(60)

            // Sets the fastest rate for active location updates.
            // This interval is exact, and your application will never
            // receive updates more frequently than this value
            fastestInterval = TimeUnit.SECONDS.toMillis(30)

            // Sets the maximum time when batched location
            // updates are delivered. Updates may be
            // delivered sooner than this interval
            maxWaitTime = TimeUnit.MINUTES.toMillis(2)

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                super.onLocationResult(locationResult)
                locationResult?.lastLocation?.let {
                    currentLocation = locationByGps
//                    latitude = currentLocation.latitude
//                    longitude = currentLocation.longitude
                    // use latitude and longitude as per your need
                } ?: {
                    Log.d("TAG", "Location information isn't available.")
                }
            }
        }
        return currentLocation
    }
}

//
//    private val api: WheatherAPI = WeatherApi.apiService
//    var weather by mutableStateOf(WeatherResult())
//
//    private val locationManager: LocationManager =
//        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
////    private lateinit var fusedLocationClient: FusedLocationProviderClient
//    var fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
//    lateinit var locationRequest : LocationRequest
//    var locationCallback: LocationCallback? = null
//    private var currentLocation: Location? = null
//
//    init {
//        fetchUserLocation()
//        getWeather()
//    }
//
//    fun getWeather(/*Location: Location? = null*/) {
//        viewModelScope.launch {
//            try {
////                if (Location != null) {
//                    weather = api.getWheather()
////                }
//            } catch (e: Exception) {
//                Log.e("ViewModel:Fetch Top Stories Ids", e.toString())
//            }
//        }
//    }
//
//    private fun requestLocationPersmissions() {
//        ActivityCompat.requestPermissions(activity, arrayOf(
//            ACCESS_FINE_LOCATION,
//            ACCESS_NETWORK_STATE
//        ), 2)
//    }
//
//    fun fetchUserLocation(): Location? {
//
//        // Localisation setting is off
//        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//            buildAlertMessageNoGps()
//            return null
//        }
//
//        // Permission not granted
//        if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            requestLocationPersmissions()
//            return null
//        }
//
////        fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)
////
////        try {
////            val locationResult = fusedLocationClient.lastLocation
////            locationResult.addOnCompleteListener { task ->
////                if (task.isSuccessful){
////                    val lastKnownLocation = task.result
////
////                    if (lastKnownLocation != null){
////                        currentLocation = lastKnownLocation
////                    }
////                }else{
////                    Log.d("Exception"," Current User location is null")
////                }
////            }
////        } catch (e: SecurityException){
////            Log.d("Exception", "Exception:  $e.message.toString()")
////        }
////
////        return currentLocation
//        // Try to get fast last known location
//        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
//            if (location != null) {
//                onLocationChanged(location)
//            }
//        }
//
//        // Request new up to date location
//        val locationRequest = LocationRequest.create().apply {
//            interval = 1000
//            fastestInterval = 1000
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
//        }
//
//        locationCallback = object: LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult?) {
//                super.onLocationResult(locationResult)
//                locationResult?.let { result ->
//                    result.locations.sortedBy { it.accuracy }.firstOrNull()?.let { bestLocation ->
//                        onLocationChanged(bestLocation)
//                        pauseLocationUpdates()
//                    }
//                }
//            }
//
//            override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
//                super.onLocationAvailability(locationAvailability)
//            }
//        }
//
//        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
//        return null
//    }
//
//    fun onLocationChanged(location: Location) {
//        currentLocation = location
//        getWeather()
//    }
//
//    private fun pauseLocationUpdates() {
//        locationCallback?.let {
//            fusedLocationClient.removeLocationUpdates(it)
//        }
//    }
//
//    private fun buildAlertMessageNoGps() {
//        val builder = AlertDialog.Builder(activity)
//        builder.setMessage(context.getString(R.string.enable_location))
//            .setCancelable(false)
//            .setPositiveButton(context.getString(R.string.answer_positive)) { _, _ ->
//                context.startActivity(
//                    Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
//                )
//            }
//            .setNegativeButton(context.getString(R.string.answer_negative)) { dialog, _ -> dialog.cancel() }
//        val alert = builder.create()
//        alert.show()
//    }
//
//}