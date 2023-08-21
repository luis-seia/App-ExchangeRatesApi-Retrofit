package com.luisseia.appexchangeratesapi_retrofit.api

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface Endpoint {
    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies.json")
    fun getCurrencies() : Call<JsonObject>
    @GET("/gh/fawazahmed0/currency-api@1/latest/currencies/{from}/{to}.json")
    fun getCurrenciesRate(@Path(value ="from", encoded = true) from: String, @Path(value ="to", encoded = true) to: String) : Call<JsonObject>

    @GET("/v1/latest?access_key={api_key}&base={coin}")
    fun getExchangeRates(@Path(value ="api_key", encoded = true) api_key: String, @Path(value ="coin", encoded = true) coin: String) : Call<JsonObject>
}