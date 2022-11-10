package com.example.androidweatheraje

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import core.appModule
import core.commonModule
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.getViewModel
import org.koin.core.context.startKoin
import view.WeatherViewModel
import view.WeatherViewModelState


class WeatherApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherApplication)
            modules(appModule)
            modules(commonModule)
        }

    }
}