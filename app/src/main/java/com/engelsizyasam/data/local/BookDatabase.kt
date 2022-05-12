package com.engelsizyasam.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.engelsizyasam.domain.model.BookModel

@Database(entities = [BookModel::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract val bookDao: BookDao
}