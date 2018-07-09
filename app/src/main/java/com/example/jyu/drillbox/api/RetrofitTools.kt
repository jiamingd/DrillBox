package com.example.jyu.drillbox.api

import android.content.Context
import android.util.Pair
import com.example.jyu.drillbox.R
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.*
import java.util.concurrent.TimeUnit


class RetrofitTools {


    companion object {

        //TODO: Later on need to check how to integrate with
        //                .addCallAdapterFactory(new PaginatedCallAdapter.Factory())
        //                .addCallAdapterFactory(new SimpleCallAdapter.Factory())
        //
        internal fun getRetrofit(context: Context): Retrofit {
            val httpClientBuilder = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .followRedirects(false)
                    .followSslRedirects(false)

                    .addInterceptor {
                        val requestBuilder = it.request().newBuilder()
                        if (it.request().header("Authorization") == null) {
                            requestBuilder.addHeader("Authorization", "Bearer PFFT4MYCYPF5EVJPHDJH")
                        }
                        requestBuilder.header("User-Agent", "User-Agent: EventbriteAttendeeAlpha/5.5.0-alpha(10000000) Android/24")
                it.proceed(requestBuilder.build())
                    }


            return Retrofit.Builder()
                    .baseUrl("https://www.evbqaapi.com/v3/")
                    .client(httpClientBuilder.build())
                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(new PaginatedCallAdapter.Factory())
//                    .addCallAdapterFactory(SimpleCallAdapter.Factory())
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
//            builder.registerTypeAdapterFactory(FlattenTypeAdapterFactory())
//            builder.registerTypeAdapter(GregorianCalendar::class.java, CalendarTypeAdapter())
//            builder.registerTypeAdapter(Calendar::class.java, CalendarTypeAdapter())
//            builder.registerTypeAdapter(Date::class.java, JavaDateDeserializer())
//            builder.registerTypeAdapter(Pair<*, *>::class.java, PairDeserializer())
            return builder
        }
    }



}