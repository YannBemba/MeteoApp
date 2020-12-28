package com.example.meteoapp.openweathermap

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

private const val API_KEY = "f23c9faad77314f6a502856cedac25ce"

interface OpenWeatherService {

    @GET("data/2.5/weather?units=metric&lang=fr")
    fun getWeather(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = API_KEY
    ): Call<WeatherWrapper>
}