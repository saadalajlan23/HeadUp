package com.example.headup

import com.example.headup.Celebrity
import retrofit2.Call
import retrofit2.http.GET

interface APIInterface {
    @GET("/celebrities/")
    fun getAllCelebrity(): Call<Celebrity>
}