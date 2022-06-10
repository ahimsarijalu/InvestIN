package com.ahimsarijalu.investin.data.datasource.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ahimsarijalu.investin.data.datasource.remote.response.DataItem
import com.ahimsarijalu.investin.utils.Converters
import kotlinx.serialization.ExperimentalSerializationApi

@ExperimentalSerializationApi
@Database(
    entities = [DataItem::class, RemoteKeys::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class ExploreDatabase : RoomDatabase() {
    abstract fun exploreDao(): ExploreDao
    abstract fun remoteKeysDao(): RemoteKeysDao

    companion object {
        @Volatile
        private var INSTANCE: ExploreDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): ExploreDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    ExploreDatabase::class.java, "explore_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}