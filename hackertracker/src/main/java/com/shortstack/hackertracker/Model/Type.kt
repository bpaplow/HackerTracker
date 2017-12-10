package com.shortstack.hackertracker.Model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Chris on 12/9/2017.
 */
@Entity(tableName = "types",
        indices = arrayOf(Index(value = "type", name = "type")))
data class Type(
        @PrimaryKey(autoGenerate = true)
        var index : Long = 0,
//        @PrimaryKey(autoGenerate = false)
        @SerializedName("event_type")
        var type : String? = null)