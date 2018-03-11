package com.shortstack.hackertracker.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Vendors(
        @SerializedName("vendors") var vendors: Array<Vendor>
)

data class Vendor(
        @SerializedName("title") var title: String,
        @SerializedName("description") var description: String,
        @SerializedName("link") var link: String,
        @SerializedName("partner") var partner: Int = 0
) : Serializable

