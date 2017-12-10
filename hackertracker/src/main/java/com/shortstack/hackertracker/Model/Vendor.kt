package com.shortstack.hackertracker.Model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.io.Serializable

data class Vendors(
        var vendors : Array<Vendor>
)

@Entity(tableName = "vendors")
data class Vendor(

        @PrimaryKey(autoGenerate = true)
        var id : Long = 0,
        var title : String? = null,
        var description : String? = null,
        var link : String? = null,
        var partner : Int = 0
) : Serializable

