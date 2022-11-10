package view

import android.location.Location
import androidx.lifecycle.ViewModel
import domain.WeatherCityDomain
import domain.WeatherItemDomain
import kotlinx.coroutines.flow.flow
import repository.WeatherRepositoryInterface
import ressource.Status

data class WeatherViewModelState(
    var city: WeatherCityDomain? = null,
    var first: WeatherItemDomain? = null,
    var items: List<WeatherItemDomain> = emptyList(),
    var isLoading: Boolean = false,
)

class WeatherViewModel(private val repository: WeatherRepositoryInterface) :
    ViewModel() {

    fun cityChanged(value: String) = fetchWeather(value)

    fun locationChanged(value: Location) = fetchWeather(value)

    private fun fetchWeather(location: Location) = fetchWithFlow(null, location)

    private fun fetchWeather(city: String) = fetchWithFlow(city)

    private fun fetchWithFlow(city: String? = null, location: Location? = null) = flow {
        val flow = if (city != null) repository.fetchWeather(city) else repository.fetchWeather(
            location!!.latitude,
            location!!.longitude
        )
        flow.collect {
            val state = WeatherViewModelState(
                city = it.data?.city,
                first = it.data?.items?.firstOrNull(),
                items = it.data?.items ?: listOf(),
                isLoading = it.status == Status.LOADING
            )
            emit(
                state
            )
        }
    }
}