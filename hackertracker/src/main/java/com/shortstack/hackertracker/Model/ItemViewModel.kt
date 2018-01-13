package com.shortstack.hackertracker.Model

import android.content.Context
import android.text.TextUtils
import android.view.View
import com.shortstack.hackertracker.Application.App
import com.shortstack.hackertracker.R
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class ItemViewModel(val item : ScheduleItem) {


    val title : String
        get() {
            val title = item.title
            if (!TextUtils.isEmpty(title) && title!!.endsWith("\n"))
                return title.substring(0, title.indexOf("\n"))
            return title!!
        }

    val description : String
        get() {
            var description : String = item.description!!
            description = description.replace("[.]. {2}".toRegex(), ".\n\n")
            description = description.replace("\n ".toRegex(), "\n")
            return description
        }

    val categoryColorPosition : Int
        get() {
            if (TextUtils.isEmpty(item.type))
                return EMPTY_CATEGORY

//            val types = App.application.database.types
//
//            for (i in types.indices) {
//                if (item.type == types[i].type)
//                    return i
//            }

            return EMPTY_CATEGORY
        }



    val progress : Float
        get() {
            return 0f
//            if (!item.hasBegin())
//                return 0f
//
//            val beginDateObject = item.beginDateObject
//            val endDateObject = item.endDateObject
//            val currentDate = App.getCurrentDate()
//
//            val length = ((endDateObject.time - beginDateObject.time) / 1000 / 60).toFloat()
//            val p = ((endDateObject.time - currentDate.time) / 1000 / 60).toFloat()
//
//            if (p == 0f)
//                return 1f
//
//            val l = p / length
//
//            return Math.min(1.0f, 1 - l)
        }

    fun getFullTimeStamp(context : Context) : String {
//        val begin = item.beginDateObject
//        val end = item.endDateObject
//
//        return String.format(context.getString(R.string.timestamp_full), item.dateStamp, getTimeStamp(context, begin), getTimeStamp(context, end))

        return "TBA"
    }


    /*(BuildConfig.DEBUG ? mItem.getIndex() + " " : "") +*/ val displayTitle : String
        get() = item.title!!

    fun hasDescription() : Boolean {
        return !TextUtils.isEmpty(item.description)
    }

    fun hasUrl() : Boolean {
        return !TextUtils.isEmpty(item.link)
    }


    fun getDetailsDescription(context : Context) : String {
        var result = ""

        result = result + (item.title!! + "\n")

        result = result + (getFullTimeStamp(context) + "\n")
        result = result + (item.location!! + "\n")
        //result = result.concat(getType());


        return result
    }

    val location : String
        get() = item.location ?: ""

    val id : Long
        get() = item.index

    val toolsVisibility : Int
        get() = if (item.isTool) View.VISIBLE else View.GONE
//        get() = View.VISIBLE

    val exploitVisibility : Int
        get() = if (item.isExploit) View.VISIBLE else View.GONE
//        get() = View.VISIBLE

    val demoVisibility : Int
        get() = if (item.isDemo) View.VISIBLE else View.GONE
//        get() = View.VISIBLE

    val bookmarkVisibility : Int
        get() = if (item.isBookmarked) View.VISIBLE else View.INVISIBLE
//        get() = View.VISIBLE

//    val speakers : Array<Speaker>
//        get() = item.speakers!!

    companion object {

        private val EMPTY_CATEGORY = 0

        fun getTimeStamp(context : Context, date : Date?) : String {
            // No start time, return TBA.
            if (date == null)
                return context.resources.getString(R.string.tba)

            val time : String
            val writeFormat : DateFormat

            if (App.application.storage.shouldShowMilitaryTime()) {
                writeFormat = SimpleDateFormat("HH:mm")
            } else {
                writeFormat = SimpleDateFormat("h:mm aa")
            }

            time = writeFormat.format(date)

            return time
        }
    }

}
