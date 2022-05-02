package com.maxence.weatherapp

import android.util.Log
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun NavComponent(viewModel: WeatherViewModel) {
    val navController = rememberNavController()

    val scaffoldState: ScaffoldState = rememberScaffoldState(
        snackbarHostState = SnackbarHostState()
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    val currentDestination = getNavDestination(navController = navController)
                    if (currentDestination?.hierarchy?.any { it.route == "home" } != true) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Filled.ArrowBack, "", tint = Color.White)
                        }
                    }
                    Text(text = "Weather", color = Color.White)
                },
                backgroundColor = Color(0xFF164255),
            )
        },
//        floatingActionButton = {
//            val currentDestination = getNavDestination(navController = navController)
//            if (currentDestination?.hierarchy?.any { it.route == "home" } == true) {
//                IconButton(
//                    modifier = Modifier
//                        .size(32.dp)
//                        .clip(CircleShape),
//                    onClick = { navController.navigate("takePicture") }) {
//                    Icon(Icons.Filled.Camera, "", tint = Color.White)
//                }
//            }
//        },
        content = {
            NavHost(navController = navController, startDestination = "home") {
                composable("home") { HomeScreen(navController, viewModel) }
//                composable("takepicture") {
//                    cameraExecutor = Executors.newSingleThreadExecutor()
//                    CameraView(viewModel.outputDirectory, cameraExecutor,
//                        onImageCaptured = {
//                            viewModel.handleImageCapture(it)
//                            viewModel.fetchPhotos()
//                            GlobalScope.launch(Dispatchers.Main) {
//                                navController.popBackStack()
//                            }
//                        }
//                    ) { Log.e("PhotoList", "View error:", it) } }
//                composable("detail/{id}") { backStackEntry ->
//                    val photoId = backStackEntry.arguments?.getString("id")
//                    DetailScreen(photoId!!, viewModel)
//                }
            }
        },
        backgroundColor = Color(0xFFE1E2E1),
    )
}

@Composable
fun getNavDestination(navController: NavHostController): NavDestination? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination
}