package com.eda.androidsamples.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context

/**
 * Created by kobayashiryou on 2017/11/02.
 *
 * データベース
 */

@Database(
    entities = arrayOf(
        User::class
    ),
    version = 1,
    exportSchema = false)
abstract class SampleDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {

        fun create(context: Context, dbName: String): SampleDatabase {
            return Room.databaseBuilder(context, SampleDatabase::class.java, dbName)
                .build()
        }
    }
}