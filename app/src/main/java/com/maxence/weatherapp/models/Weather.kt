package com.maxence.weatherapp.models

import kotlinx.serialization.Serializable

@Serializable
data class WeatherResult(
    val weather: List<Weather>? = null,
//    val base: String? = null,
    val main: Main? = null,
//    val visibility: Long? = null,
//    val wind: Wind? = null,
//    val clouds: Clouds? = null,
//    val dt: Long? = null,
    val sys: Sys? = null,
//    val timezone: Long? = null,
//    val id: Long? = null,
    val name: String? = null,
//    val cod: Long? = null
)

data class Clouds(
    val all: Long? = null
)

data class Coord(
    val lon: Double? = null,
    val lat: Long? = null
)

@Serializable
data class Main(
    val temp: Double? = null,
    val feelsLike: Double? = null,
    val tempMin: Double? = null,
    val tempMax: Double? = null,
    val pressure: Long? = null,
    val humidity: Long? = null,
    val seaLevel: Long? = null,
    val grndLevel: Long? = null
)

@Serializable
data class Sys(
    val type: Long? = null,
    val id: Long? = null,
    val country: String? = null,
    val sunrise: Long? = null,
    val sunset: Long? = null
)

@Serializable
data class Weather(
    val id: Long? = null,
    val main: String? = null,
    val description: String? = null,
    val icon: String? = null
)

data class Wind(
    val speed: Double? = null,
    val deg: Long? = null,
    val gust: Double? = null
)