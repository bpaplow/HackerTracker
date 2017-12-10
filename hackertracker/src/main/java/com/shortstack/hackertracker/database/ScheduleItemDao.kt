package com.shortstack.hackertracker.database

import android.arch.persistence.room.*
import com.shortstack.hackertracker.Model.ScheduleItem
import io.reactivex.Flowable

/**
 * Created by Chris on 12/8/2017.
 */
@Dao
interface ScheduleItemDao {

    @Query("SELECT * FROM schedule")
    fun getFullSchedule() : Flowable<List<ScheduleItem>>

//    @Query("SELECT * FROM schedule " +
//            "WHERE type LIKE :arg0 ")
//    fun getFilteredSchedule(type : String) : Flowable<List<ScheduleItem>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(item : ScheduleItem)

    @Update
    fun update(item : ScheduleItem)


}