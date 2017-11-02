package com.eda.androidsamples.database

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull

/**
 * Created by kobayashiryou on 2017/11/02.
 *
 * ユーザーデータ
 */

@Entity(tableName = User.TABLE_NAME)
class User(
    @PrimaryKey
    @ColumnInfo(name = "userId")
    @NonNull
    val userId: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "age")
    val age: Int
) {
    companion object {
        const val TABLE_NAME = "user"
    }
}