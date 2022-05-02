package com.maxence.weatherapp.service

import com.maxence.weatherapp.models.WeatherResult
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.request.*

class WeatherApiService: ApiService {
    private val client = HttpClient(Android) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(kotlinx.serialization.json.Json {
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            level = LogLevel.HEADERS
        }
    }

    override suspend fun getWeather(search: String): WeatherResult {
        return client.get("https://api.openweathermap.org/data/2.5/weather?q=${search}&appid=XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX")
    }
}

interface ApiService {
    suspend fun getWeather(search: String): WeatherResult
}