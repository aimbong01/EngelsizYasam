/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.engelsizyasam.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update


/**
 * Defines methods for using the SleepNight class with Room.
 */
@Dao
interface BookDatabaseDao {

    @Insert
    suspend fun insert(night: BookModel)

    @Update
    suspend fun update(night: BookModel)

    @Query("SELECT * FROM book_table ORDER BY bookId ASC")
    fun getAllBooks(): LiveData<List<BookModel>>

    @Query("SELECT * from book_table WHERE bookId = :key")
    fun getBookWithId(key: Int): LiveData<BookModel>

    @Query("DELETE FROM book_table")
    suspend fun clear()


}
