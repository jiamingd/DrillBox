package com.example.jyu.drillbox.databases

import android.arch.persistence.room.*
import com.example.jyu.drillbox.objects.UserProfile

@Dao
interface UserProfileDao {

    @get:Query("select * from user_profile")
    val all: List<UserProfile>

    @Insert
    fun add(userProfile: UserProfile)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(userProfile: UserProfile)
}