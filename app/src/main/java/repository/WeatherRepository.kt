package repository

import api.OpenWeatherApi
import domain.WeatherResultDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mapper.toDomain
import org.koin.core.component.KoinComponent
import ressource.Resource

class WeatherRepository(private val openWeatherApi: OpenWeatherApi) : KoinComponent,
    WeatherRepositoryInterface {

    override suspend fun fetchWeather(
        lat: Double,
        lon: Double
    ): Flow<Resource<WeatherResultDomain?>> {
        return fetch(null, lat, lon)
    }

    override suspend fun fetchWeather(city: String): Flow<Resource<WeatherResultDomain?>> {
        return fetch(city, null, null)
    }

    private suspend fun fetch(
        city: String?,
        lat: Double?,
        lon: Double?
    ): Flow<Resource<WeatherResultDomain?>> = flow {
        emit(Resource.Loading())
        if (city != null) {
            emit(Resource.Success(openWeatherApi.fetchWeather(city).toDomain()))
        } else emit(Resource.Success(openWeatherApi.fetchWeather(lat!!, lon!!).toDomain()))
    }
}