package com.example.meteoapp.weather

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.meteoapp.App
import com.example.meteoapp.R
import com.example.meteoapp.openweathermap.WeatherWrapper
import com.example.meteoapp.openweathermap.mapOpenWeatherDataToWeather
import com.example.meteoapp.weather.WeatherFragment.Companion.EXTRA_CITY_NAME
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class WeatherFragment: Fragment() {

    private var cityName: String? = null

    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var city: TextView
    private lateinit var weatherIcon: ImageView
    private lateinit var weatherDescription: TextView
    private lateinit var temperature: TextView
    private lateinit var humidity: TextView
    private lateinit var pressure: TextView

    companion object {
        val EXTRA_CITY_NAME  = "com.example.meteoapp.weather.EXTRA_CITY_NAME"
        fun newInstance() = WeatherFragment()

    }

    private val TAG = WeatherFragment:: class.java.simpleName

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_weather, container, false)

        city = view.findViewById(R.id.city)
        weatherIcon = view.findViewById(R.id.weather_icon)
        weatherDescription = view.findViewById(R.id.weather_description)
        temperature = view.findViewById(R.id.temperature)
        humidity = view.findViewById(R.id.humidity)
        pressure = view.findViewById(R.id.pressure)

        refreshLayout.setOnRefreshListener { refreshWeather() }

        return view
    }

    private fun refreshWeather() {
        updateWeatherForCity(cityName)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(activity?.intent!!.hasExtra(EXTRA_CITY_NAME)) {
            updateWeatherForCity(activity!!.intent.getStringExtra(EXTRA_CITY_NAME))
        }

    }

    private fun updateWeatherForCity(cityName: String?) {
        this.cityName = cityName

        val call = App.weatherService.getWeather("${cityName}, fr")

        if(!refreshLayout.isRefreshing){
            refreshLayout.isRefreshing = true
        }

        call.enqueue(object: retrofit2.Callback<WeatherWrapper> {

            override fun onResponse(
                call: Call<WeatherWrapper>,
                response: Response<WeatherWrapper>
            ) {
                response.body().let {
                    val weather = mapOpenWeatherDataToWeather(it!!)
                    updateUi(weather)
                    Log.i(TAG, "OpenWeatherMap response: $weather")
                    refreshLayout.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<WeatherWrapper>, t: Throwable) {
                Log.e(TAG, "Could not load city weather", t)

                refreshLayout.isRefreshing = false
                Toast.makeText(
                    activity,
                    getString(R.string.weather_message_error_could_not_weather),
                    Toast.LENGTH_SHORT
                ).show()

            }

        })
    }

    @SuppressLint("StringFormatInvalid", "StringFormatMatches")
    private fun updateUi(weather: Weather) {

        Picasso.get()
            .load(weather.iconUrl)
            .placeholder(R.drawable.ic_cloud_off)
            .into(weatherIcon)

        weatherDescription.text = weather.description
        temperature.text = getString(R.string.weather_temperature_value, weather.temperature.toInt())
        humidity.text = getString(R.string.weather_humidity_value, weather.humidity)
        pressure.text = getString(R.string.weather_pressure_value, weather.pressure)
    }



}