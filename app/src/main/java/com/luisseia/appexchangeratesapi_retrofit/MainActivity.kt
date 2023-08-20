 package com.luisseia.appexchangeratesapi_retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.gson.JsonObject

import com.luisseia.appexchangeratesapi_retrofit.api.Endpoint
import com.luisseia.appexchangeratesapi_retrofit.databinding.ActivityMainBinding
import com.luisseia.appexchangeratesapi_retrofit.util.NetworkUtil
import retrofit2.Call
import retrofit2.Response

import javax.security.auth.callback.Callback
import kotlin.Exception
import kotlin.math.roundToInt

 class MainActivity : AppCompatActivity() {
     private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getCurrencies()

        binding.button.setOnClickListener {convert()}
    }

     fun convert(){
         try {
             val retrofitClient = NetworkUtil.getRetrofitInstance("https://cdn.jsdelivr.net/")
             val endpoint = retrofitClient.create(Endpoint::class.java)

             endpoint.getCurrenciesRate(binding.spinnerFrom.selectedItem.toString(), binding.spinnerTo.selectedItem.toString()).enqueue(
                 object : retrofit2.Callback<JsonObject> {
                     override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                         var data = response.body()?.entrySet()?.find { it.key == binding.spinnerTo.selectedItem.toString() }
                         var rate : Double = data?.value.toString().toDouble()
                         val conversion = binding.editValue.text.toString().toDouble() * rate

                         val random = conversion

                         val roundoff = (random * 10000.0).roundToInt() / 10000.0

                         binding.textResponse.text = "Result: $roundoff "
                     }

                     override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                         Toast.makeText(this@MainActivity, "Falha na conexao", Toast.LENGTH_SHORT)
                     }

                 })
         }catch (e: Exception){
             Toast.makeText(this@MainActivity, "Prencha o valor", Toast.LENGTH_SHORT)

         }

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

                val posMZN = data.indexOf("mzn")
                val posUSD = data.indexOf("usd")

               val adapter = ArrayAdapter(baseContext, android.R.layout.simple_spinner_item, data)
                binding.spinnerFrom.adapter = adapter
                binding.spinnerTo.adapter = adapter

                binding.spinnerFrom.setSelection(posMZN)
                binding.spinnerTo.setSelection(posUSD)

            }

            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                println("error")
            }

        })

    }
}