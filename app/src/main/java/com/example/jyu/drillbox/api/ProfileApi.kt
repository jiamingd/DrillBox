package com.example.jyu.drillbox.api

import android.content.Context
import android.util.Log
import com.example.jyu.drillbox.databases.AppDb
import com.example.jyu.drillbox.objects.UserProfile
import com.example.jyu.drillbox.utils.EnumUtils
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonNull
import com.google.gson.JsonObject
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

            r.enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                    Log.i("", "")
                    val token = response?.body()?.get("oauth_token")?.asString

                    val fallBack = "{\"emails\":[{\"email\":\"jiaming.yu@evbqa.com\",\"verified\":true,\"primary\":true}],\"id\":\"226911043956\",\"name\":\"jiaming yu\",\"first_name\":\"jiaming\",\"last_name\":\"yu\",\"is_public\":false,\"image_id\":null,\"oauth_token\":\"5X5AWA2OFK4W3MEDOJ2X\"}"

//                    val responseBody = response?.body()?:fallBack
                    val responseBody = response?.body()?.toString()?:fallBack

                    // Debug only
// {"emails":[{"email":"jiaming.yu@evbqa.com","verified":true,"primary":true}],"id":"226911043956","name":"jiaming yu","first_name":"jiaming","last_name":"yu","is_public":false,"image_id":null,"oauth_token":"5X5AWA2OFK4W3MEDOJ2X"}
//TODO: email collection resolve



                    val userProfile = RetrofitTools.gson().fromJson(responseBody, UserProfile::class.java)

                    //TODO: Should directly store to db
                    val ups = AppDb.getInstance(context).userProfileDao().all


                    AppDb.getInstance(context).userProfileDao().update( userProfile )
                    val ups2 = AppDb.getInstance(context).userProfileDao().all


                }

                override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    Log.i("", "")
                }
            })

        } catch (e: Exception) {
            val x = e
        }

        return ""
    }


    companion object {
        @JvmStatic
        fun buildForInjection(context: Context): ProfileApi {
            return ProfileApi(context)
        }
    }
}

//TODO: Below should later be used for login device object
fun jsonObject(vararg params: Pair<String, Any?>, includeNulls: Boolean = true): JsonObject {
    val json = JsonObject()
    params.forEach {
        val key = it.first
        val value = it.second
        when (value) {
            null -> if (includeNulls) json.add(key, JsonNull.INSTANCE)
            is String -> json.addProperty(key, value)
            is Number -> json.addProperty(key, value)
            is Boolean -> json.addProperty(key, value)
            is Enum<*> -> json.addProperty(key, value.serializedName)
            is List<*> -> json.add(key, jsonArray(*value.toTypedArray()))
            is JsonElement -> json.add(key, value)
        // feel free to add things in here
            else -> throw RuntimeException("bad type ${value::class}")
        }
    }
    return json
}

fun jsonArray(vararg entries: Any?): JsonArray {
    val json = JsonArray()
    entries.forEach {
        when (it) {
            null -> json.add(JsonNull.INSTANCE)
            is String -> json.add(it)
            is Boolean -> json.add(it)
            is Number -> json.add(it)
            is JsonElement -> json.add(it)
            is Enum<*> -> json.add(it.serializedName)
        // feel free to add things in here
            else -> throw RuntimeException("bad type ${it::class}")
        }
    }
    return json
}

val Enum<*>?.serializedName: String?
    get() = EnumUtils.getSerializedName(this)
