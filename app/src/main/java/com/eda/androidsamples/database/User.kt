package com.eda.androidsamples.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.annotation.NonNull

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