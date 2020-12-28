package com.example.meteoapp.openweathermap

import com.google.gson.annotations.SerializedName

data class WeatherWrapper(
    val weather: Array<WeatherData>,
    val main: MainData
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeatherWrapper

        if (!weather.contentEquals(other.weather)) return false
        if (main != other.main) return false

        return true
    }

    override fun hashCode(): Int {
        var result = weather.contentHashCode()
        result = 31 * result + main.hashCode()
        return result
    }
}

data class WeatherData(
    val description: String,
    val icon: String
)

data class MainData(
    //Spécifiez que cet attribut est egal à l'attribut Json "temp"
    @SerializedName("temp") val temperature: Float,
    val pressure: Int,
    val humidity: Int
)
