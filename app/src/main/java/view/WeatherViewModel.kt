package view

import android.location.Location
import androidx.lifecycle.ViewModel
import domain.WeatherDomain
import kotlinx.coroutines.flow.flow
import repository.WeatherRepositoryInterface

class WeatherViewModel {
    data class WeatherViewModelState(
        var city: WeatherDomain.WeatherCityDomain? = null,
        var first: WeatherDomain.WeatherItemDomain? = null,
        var items: List<WeatherDomain.WeatherItemDomain> = emptyList(),
        var isLoading: Boolean = false,
    )

    class WeatherViewModel(private val repository: WeatherRepositoryInterface) :
        ViewModel() {

        fun cityChanged(value: String) = fetchWeather(value)

        fun locationChanged(value: Location) = fetchWeather(value)

        private fun fetchWeather(location: Location) = fetchWithFlow(null, location)

        private fun fetchWeather(city: String) = fetchWithFlow(city)

        private fun fetchWithFlow(city: String? = null, location: Location? = null) = flow {
            city.let {
                repository.fetchWeather(it!!)
            }
            location.let {
                repository.fetchWeather(it!!, it)
            }
        }
    }
}