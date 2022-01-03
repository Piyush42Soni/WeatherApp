package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.example.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        var name_: String
        binding.button.setOnClickListener {
            binding.tvCity.text = binding.e1.text
            name_ = (binding.e1.text.toString()).replace(' ', '+')
            if (name_.isEmpty()) {
                "Error".also { binding.tvMain.text = it }
                "Error".also { binding.tvTemp.text = it }
                "Please Enter Full and valid name of city".also { binding.tvCity.text = it }

            } else {
                val url =
                    "https://api.openweathermap.org/data/2.5/weather?q=${name_}&appid=9b6c8787643a01f45d089ddf2bc469e3"
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null,
                    { response ->
                        binding.tvMain.text =
                            response.getJSONArray("weather").getJSONObject(0).getString("main")
                        binding.tvTemp.text = response.getJSONObject("main").getString("temp")
                    }
                ) { error: VolleyError ->
                    "Please Try Again".also { binding.tvMain.text = it }
                    "Error".also { binding.tvTemp.text = it }
                }
                MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest)
            }
            val p = binding.tvMain.text.toString()
            if (p.contains("ist") or p.contains("og")) {
                binding.imageView.setImageResource(R.mipmap.fog)
            } else if (p.contains("lear") or p.contains("unny")) {
                binding.imageView.setImageResource(R.mipmap.clear)
            } else if (p.contains("hunderstorm")) {
                binding.imageView.setImageResource(R.mipmap.thunderstorm)
            } else if (p.contains("now")) {
                binding.imageView.setImageResource(R.mipmap.snow)
            } else if (p.contains("rain") or p.contains("Rain")) {
                binding.imageView.setImageResource(R.mipmap.light_rain)
            } else {
                binding.imageView.setImageResource(R.mipmap.partly_cloudy)
            }
        }
    }
}