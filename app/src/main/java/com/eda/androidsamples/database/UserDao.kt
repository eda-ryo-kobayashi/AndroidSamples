package com.eda.androidsamples.database

import android.arch.persistence.room.*

import io.reactivex.Flowable

/**
 * Created by kobayashiryou on 2017/11/02.
 *
 * ユーザーデータインターフェース
 */

@Dao
interface UserDao {

    @Query("select * from " + User.TABLE_NAME)
    fun getAll(): Flowable<List<User>>

    @Query("select * from " + User.TABLE_NAME + " where userId = :userId")
    fun getById(userId: String): Flowable<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(user: User): Long

    @Delete
    fun delete(user: User)

    @Query("delete from " + User.TABLE_NAME)
    fun deleteAll()
}
