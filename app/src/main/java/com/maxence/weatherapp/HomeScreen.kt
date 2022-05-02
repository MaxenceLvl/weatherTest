package com.maxence.weatherapp

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

@Composable
fun HomeScreen(navController: NavController, viewModel: WeatherViewModel) {

    val weatherResult = viewModel.weatherResults.weather

    val desc = if (weatherResult.isNullOrEmpty()) "No desc" else viewModel.weatherResults.weather?.get(0)?.description
    Text(text = if (desc.isNullOrEmpty()) "No weather" else desc)
}