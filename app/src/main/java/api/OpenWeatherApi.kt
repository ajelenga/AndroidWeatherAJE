package api

import dto.WeatherDto

class OpenWeatherApi {
    suspend fun fetchWeather(city: String): WeatherDto.WeatherResultDto {
        return null;
    }
    suspend fun fetchWeather(lat: Double, lon : Double): WeatherDto.WeatherResultDto {
        return null;
    }
}
