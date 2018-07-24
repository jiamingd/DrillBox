package com.example.jyu.drillbox.objects

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "user_profile")
class UserProfile(id: String, var firstName: String?, var lastName: String?, var oauthToken: String?){
    @PrimaryKey
    var id = id

    //TODO: email collection resolve


}
