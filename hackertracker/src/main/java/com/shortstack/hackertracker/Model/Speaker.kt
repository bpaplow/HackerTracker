package com.shortstack.hackertracker.Model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Speakers(
        val speakers: Array<Speaker>
)

@Entity(tableName = "speakers")
data class Speaker(

        @SerializedName("sptitle")
        var title: String? = null,
        @SerializedName("who")
        var name: String? = null,
        @PrimaryKey
        @SerializedName("indexsp")
        var id: Long = 0,
        var lastUpdate: String? = null,
        var media: String? = null,
        var bio: String? = null

) : Serializable

