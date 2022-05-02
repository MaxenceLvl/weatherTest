package com.maxence.weatherapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter

@Composable
fun HomeScreen(navController: NavController, viewModel: WeatherViewModel) {

    val weatherResult = viewModel.weatherResults.weather

    val desc = if (weatherResult.isNullOrEmpty()) "No desc" else viewModel.weatherResults.weather?.get(0)?.description
    val city = if (weatherResult.isNullOrEmpty()) "No city" else viewModel.weatherResults.name
    val icon = if (weatherResult.isNullOrEmpty()) "No icon" else viewModel.weatherResults.weather?.get(0)?.icon
    val url = if (icon === "No icon") "" else "http://openweathermap.org/img/wn/$icon@2x.png"
    val country = if (weatherResult.isNullOrEmpty()) "No country" else viewModel.weatherResults.sys?.country
    val temp = if (weatherResult.isNullOrEmpty()) "No temp" else viewModel.weatherResults.main?.temp.toString()

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {
            Text(
                text = if (country.isNullOrEmpty()) "No country" else "$city, $country",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp
            )
        }
        Image(
            painter = rememberImagePainter(
                data = url,
            ),
            contentDescription = null,
            modifier = Modifier.size(100.dp)
        )
        Text(
            text = if (temp.isEmpty()) "No desc" else "$temp°C",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )

        Text(text = if (desc.isNullOrEmpty()) "No weather" else desc)
    }
}