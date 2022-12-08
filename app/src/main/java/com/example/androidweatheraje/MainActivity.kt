package com.example.androidweatheraje

import LocationViewModel
import PermissionAction
import SetupNavGraph
import android.Manifest
import android.content.Context
import android.location.Location
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.androidweatheraje.ui.theme.AndroidWeatherAJETheme
import components.WeatherSearchScreen
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import view.WeatherViewModel
import view.WeatherViewModelState

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalComposeUiApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidWeatherAJETheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    WeatherList("Brest")
                    val navHostController = rememberNavController()
                    //affichage de départ
                    //WeatherSearchScreen(navHostController = navHostController)
                    //val navHostController = rememberNavController()
                    SetupNavGraph(navHostController = navHostController)
                }

            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AndroidWeatherAJETheme {
        Greeting("Android")
    }
}


@Composable
fun WeatherList(name: String) {
    val weatherViewModel = getViewModel<WeatherViewModel>()
    val state by remember(weatherViewModel) {
        weatherViewModel.cityChanged(name)
    }.collectAsState(initial = WeatherViewModelState())

    Column() {
        Text(text = "Hello $name!")
        state.items.let {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                items(items = it, itemContent = { item ->
                    Row() {
                        Text(text = "Le ${item.day} à ${item.hour}H ==> ")
                        Text(
                            text =
                            when (item.image) {
                                "1" -> "beau"
                                "2" -> "moyen"
                                "3" -> "pluie"
                                else -> ":/"
                            }
                        )
                    }
                })
            }
        }
    }
}