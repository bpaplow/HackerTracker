package com.shortstack.hackertracker.database

import com.google.gson.annotations.SerializedName
import java.util.*

data class Patches(@SerializedName("patches") val patches: Array<Patch>) {

    data class Patch(
            @SerializedName("version") val version: Int = 0,
            @SerializedName("commands") val commands: Array<String>) {

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as Patch

            if (version != other.version) return false
            if (!Arrays.equals(commands, other.commands)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = version
            result = 31 * result + Arrays.hashCode(commands)
            return result
        }

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Patches

        if (!Arrays.equals(patches, other.patches)) return false

        return true
    }

    override fun hashCode(): Int {
        return Arrays.hashCode(patches)
    }

}
