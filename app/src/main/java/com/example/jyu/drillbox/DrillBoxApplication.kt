package com.example.jyu.drillbox

import android.app.Application
import android.arch.persistence.room.Room
import com.example.jyu.drillbox.databases.AppDb

class DrillBoxApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        val database = AppDb.getInstance(ctx = this@DrillBoxApplication)


    }

}