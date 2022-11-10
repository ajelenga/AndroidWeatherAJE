package mapper

import domain.WeatherDomain
import dto.WeatherDto
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class Mapper {
    fun WeatherDto.WeatherResultDto.toDomain() = WeatherDomain.WeatherResultDomain(
        city = this.city?.toDomain(),
        items = this.list?.map { it.toDomain() },
    )

    fun WeatherDto.WeatherCityDto.toDomain() = WeatherDomain.WeatherCityDomain(
        name = this.name,
        coord = this.coord.toDomain(),
        population = this.population,
        sunrise = this.sunrise,
        sunset = this.sunset
    )

    fun WeatherDto.CoordinateDto.toDomain() = WeatherDomain.CoordinateDomain(
        lat = this.lat,
        lon = this.lon
    )

    fun WeatherDto.WeatherItemDto.toDomain() = WeatherDomain.WeatherItemDomain(
        date = LocalDateTime.parse(this.dt_txt, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
        image = when {
            main.humidity < 60 -> "1"
            main.humidity < 80 -> "2"
            else -> "3"
        },
        infos = this.main.toDomain()
    ).apply {
        this.day = date.dayOfMonth.toString()
        this.hour = date.hour.toString()
    }

    fun WeatherDto.WeatherInfoDto.toDomain() = WeatherDomain.WeatherInfoDomain(
        temp = this.temp,
        humidity = this.humidity,
        pressure = this.pressure
    )
}