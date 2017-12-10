package com.shortstack.hackertracker.Model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Chris on 12/8/2017.
 */

@Entity(tableName = "schedule")
data class ScheduleItem(
        @PrimaryKey(autoGenerate = false)
        @ColumnInfo(name = "id")
        var index : Long = 0,
        @SerializedName("entry_type")
        var type : String? = null,
        var title : String? = null,
        //        var speakers : Array<Speakers>? = null,
        var location : String? = null,
        var description : String? = null,
        @SerializedName("start_date")
        var begin : String? = null,
        @SerializedName("end_date")
        var end : String? = null,
        var link : String? = null,
        var dctvChannel : String? = null,
        var includes : String? = null,
        @SerializedName("updated_at")
        var updatedAt : String = "",
        var bookmarked : Int = 0

) : Serializable {

    val isTool
        get() = includes?.contains("Tool") == true

    val isExploit
        get() = includes?.contains("Exploit") == true

    val isDemo
        get() = includes?.contains("Demo") == true

    val isBookmarked
        get() = bookmarked == 1
}