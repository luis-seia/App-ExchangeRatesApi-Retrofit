 package com.luisseia.appexchangeratesapi_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.gson.JsonObject

import com.luisseia.appexchangeratesapi_retrofit.api.Endpoint
import com.luisseia.appexchangeratesapi_retrofit.databinding.ActivityMainBinding
import com.luisseia.appexchangeratesapi_retrofit.util.NetworkUtil
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

 class MainActivity : AppCompatActivity() {
     private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCurrencies()
    }

    fun getCurrencies(){
        val retrofitClient = NetworkUtil.getRetrofitInstance("https://cdn.jsdelivr.net/")
        val endpoint = retrofitClient.create(Endpoint::class.java)

        endpoint.getCurrencies().enqueue(object : retrofit2.Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
               var data = mutableListOf<String>()

                response.body()?.keySet()?.iterator()?.forEach {
                    data.add(it)
                }
                println(data.count())
            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                println("error")
            }

        })

    }
}