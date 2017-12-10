package com.shortstack.hackertracker.Application

import android.app.Application
import android.arch.persistence.room.Room
import android.content.Context
import android.preference.PreferenceManager
import com.crashlytics.android.Crashlytics
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import com.firebase.jobdispatcher.GooglePlayDriver
import com.firebase.jobdispatcher.Lifetime
import com.firebase.jobdispatcher.Trigger
import com.github.stkent.amplify.tracking.Amplify
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.orhanobut.logger.Logger
import com.shortstack.hackertracker.Analytics.AnalyticsController
import com.shortstack.hackertracker.BuildConfig
import com.shortstack.hackertracker.Common.Constants
import com.shortstack.hackertracker.Event.MainThreadBus
import com.shortstack.hackertracker.R
import com.shortstack.hackertracker.Task.SyncJob
import com.shortstack.hackertracker.Utils.NotificationHelper
import com.shortstack.hackertracker.Utils.SharedPreferencesUtil
import com.shortstack.hackertracker.Utils.TimeHelper
import com.shortstack.hackertracker.database.MyDatabase
import com.squareup.otto.Bus
import io.fabric.sdk.android.Fabric
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*


class App : Application() {

    lateinit var appContext : Context
        private set

    // Eventbus
    val bus : Bus by lazy { MainThreadBus() }
    // Storage
    val storage : SharedPreferencesUtil by lazy { SharedPreferencesUtil() }
    // Notifications
    val notificationHelper : NotificationHelper by lazy { NotificationHelper(appContext) }
    // Analytics
    val analyticsController : AnalyticsController by lazy { AnalyticsController() }
    // Time
    val timeHelper : TimeHelper by lazy { TimeHelper(appContext) }

    val gson : Gson by lazy { GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create() }

    val dispatcher : FirebaseJobDispatcher by lazy { FirebaseJobDispatcher(GooglePlayDriver(appContext)) }


    lateinit var database : MyDatabase


    override fun onCreate() {
        super.onCreate()

        init()
        initFabric()
        initLogger()
        initFeedback()

//        updateDatabaseController()

        if (!storage.isSyncScheduled) {
            storage.setSyncScheduled()
            scheduleSync()
        }

        database = Room.databaseBuilder(this, MyDatabase::class.java, "database-name").build()

        Single.fromCallable {
            database.createDatabase()
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe()


    }

    fun updateDatabaseController() {
        val name = if (storage.databaseSelected == 0) Constants.DEFCON_DATABASE_NAME else Constants.TOORCON_DATABASE_NAME
        setTheme(if (storage.databaseSelected == 0) R.style.AppTheme else R.style.AppTheme_Toorcon)

        Logger.d("Creating database controller with database: $name")
//        databaseController = DEFCONDatabaseController(appContext, name = name)
//
//        if (databaseController.exists()) {
//            databaseController.checkDatabase()
//        }
    }


    fun scheduleSync() {

        cancelSync()

        val hours = PreferenceManager.getDefaultSharedPreferences(this).getString("sync_interval", "6")

        var value = hours.toIntOrNull()

        if (value == null)
            value = 6

        if (value == 0) {
            cancelSync()
            return
        }

        value *= Constants.TIME_SECONDS_IN_HOUR

        val job = dispatcher.newJobBuilder()
                .setService(SyncJob::class.java)
                .setTag(SyncJob.TAG)
                .setRecurring(true)
                .setLifetime(Lifetime.FOREVER)
                .setTrigger(Trigger.executionWindow(value, value + Constants.TIME_SECONDS_IN_HOUR))
                .build()

        dispatcher.mustSchedule(job)
    }

    fun cancelSync() {
        dispatcher.cancel(SyncJob.TAG)
    }

    private fun initFeedback() {
        Amplify.initSharedInstance(this)
                .setFeedbackEmailAddress(Constants.FEEDBACK_EMAIL)
                .applyAllDefaultRules()
                .setLastUpdateTimeCooldownDays(1)
    }

    private fun init() {
        application = this
        appContext = applicationContext
    }

    private fun initLogger() {
        Logger.init().methodCount(1).hideThreadInfo()
    }

    private fun initFabric() {
        if (!BuildConfig.DEBUG)
            Fabric.with(this, Crashlytics())
    }


    fun postBusEvent(event : Any) {
        bus.post(event)
    }

    fun unregisterBusListener(itemView : Any) {
        bus.unregister(itemView)
    }

    fun registerBusListener(itemView : Any) {
        bus.register(itemView)
    }


    companion object {

        lateinit var application : App

        fun getCurrentCalendar() : Calendar = application.timeHelper.currentCalendar

        fun getCurrentDate() : Date = application.timeHelper.currentDate

        fun getRelativeDateStamp(date : Date) : String = application.timeHelper.getRelativeDateStamp(date)


    }
}