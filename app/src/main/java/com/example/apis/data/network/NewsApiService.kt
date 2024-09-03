package com.example.apis.data.network


import com.example.apis.data.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface NewsApiService {
    @GET("top-headlines/category/{category}/{countryCode}.json")
    suspend fun getTopHeadlines(
        @Path("category") category: String,
        @Path("countryCode") countryCode: String
    ): NewsResponse
}


