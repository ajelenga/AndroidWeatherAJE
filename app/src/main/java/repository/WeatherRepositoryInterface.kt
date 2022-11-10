package repository

import android.location.Location
import domain.WeatherDomain
import kotlinx.coroutines.flow.Flow
import ressource.Resource

interface WeatherRepositoryInterface {
    suspend fun fetchWeather(city: String): Flow<Resource<WeatherDomain.WeatherResultDomain?>>
    suspend fun fetchWeather(lat: Location, lon: Location): Flow<Resource<WeatherDomain.WeatherResultDomain?>>
}