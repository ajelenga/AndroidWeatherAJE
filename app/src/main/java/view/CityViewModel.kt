package view

import android.location.Location
import androidx.lifecycle.ViewModel
import domain.WeatherCityDomain
import kotlin.math.absoluteValue

object LocationFormater {

    fun latitudeAsDMS(latitude: Double, decimalPlace: Int): String {
        val direction = if (latitude > 0) "N" else "S"
        var strLatitude = Location.convert(latitude.absoluteValue, Location.FORMAT_SECONDS)
        strLatitude = replaceDelimiters(strLatitude, decimalPlace)
        strLatitude += " $direction"
        return strLatitude
    }

    fun longitudeAsDMS(longitude: Double, decimalPlace: Int): String {
        val direction = if (longitude > 0) "W" else "E"
        var strLongitude = Location.convert(longitude.absoluteValue, Location.FORMAT_SECONDS)
        strLongitude = replaceDelimiters(strLongitude, decimalPlace)
        strLongitude += " $direction"
        return strLongitude
    }

    private fun replaceDelimiters(str: String, decimalPlace: Int): String {
        var str = str
        str = str.replaceFirst(":".toRegex(), "°")
        str = str.replaceFirst(":".toRegex(), "'")
        val pointIndex = str.indexOf(".")
        val endIndex = pointIndex + 1 + decimalPlace
        if (endIndex < str.length) {
            str = str.substring(0, endIndex)
        }
        str += "\""
        return str
    }
}

class CityViewModel(private val city: WeatherCityDomain): ViewModel()  {
    val initialZoom = 12f

    val label: String = city.name

    val population : Int
        get() = city.population

    val lat: Double = city.coord.lat ?: 0.0

    val lon: Double = city.coord.lon ?: 0.0

    val hasCoordinate: Boolean
        get() = city.coord.lat != null && city.coord.lon != null

    val formattedCoordinate: String
        get() {
            return if (hasCoordinate) {
                "${
                    LocationFormater.latitudeAsDMS(city.coord.lat!!, 2)
                }  ${
                    LocationFormater.longitudeAsDMS(
                        city.coord.lon!!, 2
                    )
                }"
            } else ""
        }
}