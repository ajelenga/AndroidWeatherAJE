package repository

import domain.WeatherResultDomain
import kotlinx.coroutines.flow.Flow
import ressource.Resource

interface WeatherRepositoryInterface {
    suspend fun fetchWeather(city: String): Flow<Resource<WeatherResultDomain?>>
    suspend fun fetchWeather(lat: Double, lon: Double): Flow<Resource<WeatherResultDomain?>>
}