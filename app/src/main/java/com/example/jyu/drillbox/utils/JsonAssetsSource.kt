package com.example.jyu.drillbox.utils

import android.content.Context
import com.example.jyu.drillbox.api.RetrofitTools
import com.example.jyu.drillbox.objects.UserProfile
import java.io.InputStreamReader

object JsonAssetsSource {
    fun defaultUserProfile(context: Context): UserProfile {
        val iStreamReader = InputStreamReader(context.assets.open("user_profile.json"))
        return RetrofitTools.gson().fromJson(iStreamReader, UserProfile::class.java)
    }

}