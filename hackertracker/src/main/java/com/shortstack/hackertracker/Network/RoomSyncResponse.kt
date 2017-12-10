package com.shortstack.hackertracker.Network

import com.google.gson.annotations.SerializedName
import com.shortstack.hackertracker.Model.ScheduleItem

data class RoomSyncResponse(

    @SerializedName("update_date")
    val updatedDate : String,
    val schedule: Array<ScheduleItem>

)