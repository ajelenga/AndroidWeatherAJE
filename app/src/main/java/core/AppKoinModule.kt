package core

import LocationViewModel
import android.util.Log
import api.OpenWeatherApi
import com.example.androidweatheraje.WeatherApplication
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import io.ktor.client.*
import io.ktor.client.engine.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.serialization.json.Json
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module
import repository.WeatherRepository
import repository.WeatherRepositoryInterface
import view.WeatherViewModel

private inline val requireApplication
    get() = WeatherApplication.instance ?: error("Missing call: initWith(application)")

val appModule = module {
    single<String>(named("weather_api_key")) {
        "888f70e84a4d7e44f3c0d4870c926e9d"
    }
        viewModel { WeatherViewModel(repository = get()) }
    }

    val commonModule = module {
        single { Android.create() }
        single { createJson() }
        single { createHttpClient(get(), get(), enableNetworkLogs = true) }
        single { CoroutineScope(Dispatchers.Default + SupervisorJob()) }
        single<WeatherRepositoryInterface> { WeatherRepository(get()) }
        single { OpenWeatherApi(get(), get(named("weather_api_key"))) }
        single<FusedLocationProviderClient> { LocationServices.getFusedLocationProviderClient(requireApplication.applicationContext) }
        viewModel { LocationViewModel(client =  get()) }
    }

    fun createJson() = Json { isLenient = true; ignoreUnknownKeys = true }

    fun createHttpClient(httpClientEngine: HttpClientEngine, json: Json, enableNetworkLogs: Boolean) =
        HttpClient(httpClientEngine) {
            install(ContentNegotiation) {
                json(json)
            }
            if (enableNetworkLogs) {
                install(Logging) {
                    logger = CustomHttpLogger()
                    level = LogLevel.ALL
                }
            }
        }

    class CustomHttpLogger() : Logger {
        override fun log(message: String) {
            Log.i("CustomHttpLogger", "message : $message")
        }
    }