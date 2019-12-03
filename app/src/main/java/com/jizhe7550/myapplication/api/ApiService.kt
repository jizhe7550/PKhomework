package com.jizhe7550.myapplication.api

import com.jizhe7550.myapplication.model.CharmModel
import retrofit2.http.*

interface ApiService {

    companion object {
        const val BASE_URL = "https://api.thecatapi.com"
    }

    @GET("/v1/images/search")
    suspend fun getCharms(@Query("limit") num: Int = 10): List<CharmModel>
}