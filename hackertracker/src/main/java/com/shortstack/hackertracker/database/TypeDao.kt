package com.shortstack.hackertracker.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.shortstack.hackertracker.Model.Type
import io.reactivex.Flowable

/**
 * Created by Chris on 12/9/2017.
 */
@Dao
interface TypeDao {

    @Query("SELECT * FROM types")
    fun getTypes() : Flowable<List<Type>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(type : Type)
}