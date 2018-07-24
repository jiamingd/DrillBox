package com.example.jyu.drillbox.api

import android.content.Context
import android.util.Log
import com.example.jyu.drillbox.databases.AppDb
import com.example.jyu.drillbox.objects.UserProfile
import com.example.jyu.drillbox.utils.JsonAssetsSource
import com.google.gson.JsonObject
import jsonObject
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST


interface ProfileService {
    @POST("auth/login/")
    fun login(
            @Header("Authorization") authorizationHeader: String?,
            @Body body: JsonObject
    ): Call<JsonObject>

    @POST("auth/login/")
    fun login2(
            @Header("Authorization") authorizationHeader: String?,
            @Body body: JsonObject
    ): Call<ResponseBody>
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
            val r = profileService.login(null,
                    jsonObject(
                            "email_address" to username,
                            "password" to  if (password?.length > 10) password else "Blabla"
                    ))
            var userProfile: UserProfile?

            r.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                    if (response?.code() == 503) {
                        JsonAssetsSource.defaultUserProfile(context)
                    }

                    val responseBody = response?.body()?.toString()
                    userProfile = RetrofitTools.gson().fromJson(responseBody, UserProfile::class.java)

                    //TODO: Should directly store to db
                    val ups = AppDb.getInstance(context).userProfileDao().all()

                    AppDb.getInstance(context).userProfileDao().update( userProfile )
                    val ups2 = AppDb.getInstance(context).userProfileDao().all()
                    ups2.observeForever {
                        Log.i("", "size of UserProfile: " + it)
                    }
                }

                override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    Log.i("", "")
                    userProfile = JsonAssetsSource.defaultUserProfile(context)
                }
            })

        } catch (e: Exception) {
            print("Get error in log_in")
        }
        return ""
    }
}
