package com.tombat.weatherforecastapp

import android.app.Application
import androidx.room.Room
import com.tombat.weatherforecastapp.dashboard.data.datasource.db.AppDatabase
import com.tombat.weatherforecastapp.dashboard.di.databaseModule
import com.tombat.weatherforecastapp.dashboard.di.networkModule
import com.tombat.weatherforecastapp.dashboard.di.repoModule
import com.tombat.weatherforecastapp.dashboard.di.useCaseModule
import com.tombat.weatherforecastapp.dashboard.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class MyApplication : Application() {
    companion object {
        lateinit var database: AppDatabase
    }

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApplication)
            modules(listOf(networkModule, repoModule, viewModelModule, useCaseModule, databaseModule))
        }

        database = Room.databaseBuilder(this, AppDatabase::class.java, "weather-database")
            .fallbackToDestructiveMigration()
            .build()
    }
}

