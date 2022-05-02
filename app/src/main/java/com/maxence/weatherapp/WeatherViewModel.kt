package com.maxence.weatherapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.ComponentActivity
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.*
import com.maxence.weatherapp.models.WeatherResult
import com.maxence.weatherapp.service.WeatherApiService
import kotlinx.coroutines.launch

@SuppressLint("StaticFieldLeak")
class WeatherViewModel(private val activity: ComponentActivity) : ViewModel() {

    private val apiService = WeatherApiService()
    var weatherResults: WeatherResult by mutableStateOf(WeatherResult())

    var locationByGps: Location? by mutableStateOf(null)
    private var fusedLocationProviderClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
//    private lateinit var locationRequest: LocationRequest
//    private lateinit var locationCallback: LocationCallback
    private var currentLocation: Location? by mutableStateOf(null)

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

    private fun getCurrentLocation() {
        if (checkPermissions()) {
            if (ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissions()
                return
            }
            fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
                if (location != null) {
                    currentLocation = location
                    locationByGps = location
                }
            }
        }
    }

    private fun checkPermissions(): Boolean {
        if (ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                activity,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            100
        )
    }

}