package com.example.jyu.drillbox.databases

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*
import com.example.jyu.drillbox.objects.UserProfile

@Dao
interface UserProfileDao {

    @Query("select * from user_profile")
    fun all(): LiveData<List<UserProfile>>

    @Insert
    fun add(userProfile: UserProfile)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(userProfile: UserProfile?)
}