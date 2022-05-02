package com.maxence.weatherapp.utils

import io.ktor.client.features.*
import io.ktor.util.network.*

class NetworkResponseCode {
    fun checkError(e: Throwable): Int {
        return when (e) {
            is RedirectResponseException -> {
                (e.response.status.value)
            }
            is ClientRequestException -> {
                (e.response.status.value)
            }
            is ServerResponseException -> {
                (e.response.status.value)
            }
            is UnresolvedAddressException -> {
                -1
            }
            else -> {
                -2
            }
        }
    }
}