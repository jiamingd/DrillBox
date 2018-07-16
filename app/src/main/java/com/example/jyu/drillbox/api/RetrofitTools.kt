package com.example.jyu.drillbox.api

import android.content.Context
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class RetrofitTools {

    companion object {

        internal fun getRetrofit(context: Context): Retrofit {
            val httpClientBuilder = OkHttpClient.Builder()
                    .connectTimeout(10, TimeUnit.SECONDS)
                    .readTimeout(10, TimeUnit.SECONDS)
                    .writeTimeout(10, TimeUnit.SECONDS)
                    .followRedirects(false)
                    .followSslRedirects(false)

            return Retrofit.Builder()
                    .baseUrl("https://api.github.com/")
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
        }

        // TODO: now use GsonTools.java to move on, figure out why can not ref.
        internal fun gson(): Gson {
            val builder = gsonBuilder()
            return builder.create()
        }

        internal fun gsonBuilder(): GsonBuilder {
            val builder = GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .serializeNulls()
            return builder
        }
    }



}