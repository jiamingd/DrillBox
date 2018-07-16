package com.example.jyu.drillbox.api

import android.content.Context
import android.util.Base64
import android.util.Log
import com.example.jyu.drillbox.databases.AppDb
import com.example.jyu.drillbox.objects.UserProfile
import com.example.jyu.drillbox.utils.EnumUtils
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ProfileService {
    @POST("user")
    fun login(
            @Header("Accept") accept: String,
            @Header("Authorization") authorizationHeader: String?,
            @Body body: JsonObject
    ): Call<JsonObject>
}

class ProfileApi constructor(
        val context: Context,
        val profileService: ProfileService = RetrofitTools.getRetrofit(context).create(ProfileService::class.java)
) {
    /**
     * @return oAuth token value
     */
    fun login(username: String, password: String): String {
        try {
            val basicAuthValue = username.trim() + ":" + password.trim()

            val auth = "Basic " + Base64.encodeToString(basicAuthValue.toByteArray(), Base64.NO_WRAP)  //(value.getBytes("UTF-8")

            val r = profileService.login("application/json", auth, JsonObject())

            r.enqueue(object : Callback<JsonObject> {

                override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                    Log.i("", "")
                }

                override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    Log.i("", "calling for authentication failed")
                }
            })

        } catch (e: Exception) {
            val x = e
        }

        return ""
    }

}
