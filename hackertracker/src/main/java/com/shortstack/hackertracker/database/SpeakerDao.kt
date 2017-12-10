package com.shortstack.hackertracker.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.shortstack.hackertracker.Model.Speaker
import io.reactivex.Flowable

/**
 * Created by Chris on 12/9/2017.
 */
@Dao
interface SpeakerDao {

    @Query("SELECT * FROM speakers")
    fun getSpeakers() : Flowable<List<Speaker>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(speaker : Speaker)
}