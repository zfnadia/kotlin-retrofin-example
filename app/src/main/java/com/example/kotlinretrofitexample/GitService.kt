package com.example.kotlinretrofitexample

import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface GitService {
    @GET("search/users?")
    fun getGivenUsersRepos(@Query("q") name: String? = ""): Call<GitResponse>


    companion object Factory {
        var BaseUrl = "https://api.github.com/"

        @Volatile
        private var retrofit: Retrofit? = null

        @Synchronized
        fun create(): GitService {
            retrofit ?: synchronized(this) {
                retrofit = buildRetrofit()
            }
            return retrofit?.create(GitService::class.java)!!
        }

        private fun buildRetrofit(): Retrofit {
            val gson = GsonBuilder()
                .setLenient()
                .create()

            return Retrofit.Builder()
                .baseUrl(BaseUrl)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
    }
}