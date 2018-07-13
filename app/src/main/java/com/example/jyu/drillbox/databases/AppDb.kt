package com.example.jyu.drillbox.databases

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.example.jyu.drillbox.objects.UserProfile

@Database(entities = [UserProfile::class], version = 1)
abstract class AppDb : RoomDatabase() {

    abstract fun userProfileDao(): UserProfileDao

    companion object {
        private var instance: AppDb? = null

        fun getInstance(ctx: Context): AppDb {
            if (instance == null) {
                instance = Room.databaseBuilder(ctx.applicationContext, AppDb::class.java, "drill_box")
                        .allowMainThreadQueries() //TODO: I don't like the way, door should shutdown
                        .fallbackToDestructiveMigration().build()
            }
            return instance!!
        }
    }


}