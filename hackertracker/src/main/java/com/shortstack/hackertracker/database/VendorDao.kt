package com.shortstack.hackertracker.database

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.shortstack.hackertracker.Model.Vendor
import io.reactivex.Flowable

/**
 * Created by Chris on 12/9/2017.
 */
@Dao
interface VendorDao {

    @Query("SELECT * FROM vendors")
    fun getVendors() : Flowable<List<Vendor>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vendor : Vendor)
}