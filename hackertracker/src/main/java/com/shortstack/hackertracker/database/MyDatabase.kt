package com.shortstack.hackertracker.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.shortstack.hackertracker.Application.App
import com.shortstack.hackertracker.Model.*
import com.shortstack.hackertracker.Network.RoomSyncResponse
import com.shortstack.hackertracker.fromJsonFile
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Chris on 12/8/2017.
 */
@Database(entities = arrayOf(ScheduleItem::class, Type::class, Vendor::class, Speaker::class), version = 1)
abstract class MyDatabase : RoomDatabase() {

    abstract fun scheduleItemDao() : ScheduleItemDao

    abstract fun typeDao() : TypeDao

    abstract fun vendorDao() : VendorDao

    abstract fun speakerDao() : SpeakerDao


    fun createDatabase() {

        val gson = App.application.gson

        val response = gson.fromJsonFile(SCHEDULE_FILE, RoomSyncResponse::class.java)
        for (item in response.schedule) {
            scheduleItemDao().insert(item)
        }

        val types = gson.fromJsonFile(TYPES_FILE, Types::class.java)
        for (type in types.types) {
            typeDao().insert(type)
        }

        val vendors = gson.fromJsonFile(VENDORS_FILE, Vendors::class.java)
        for (vendor in vendors.vendors) {
            vendorDao().insert(vendor)
        }

        val speakers = gson.fromJsonFile(SPEAKERS_FILE, Speakers::class.java)
        for (speaker in speakers.speakers) {
            speakerDao().insert(speaker)
        }
    }

    fun setTypes() {
        typeDao().getTypes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    types = it
                })
    }

    lateinit var types : List<Type>


    companion object {
        private val SCHEDULE_FILE = "schedule-full.json"
        private val TYPES_FILE = "event_type.json"
        private val SPEAKERS_FILE = "speakers.json"
        private val VENDORS_FILE = "vendors.json"
    }
}