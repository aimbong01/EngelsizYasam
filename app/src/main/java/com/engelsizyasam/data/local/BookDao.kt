package com.engelsizyasam.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.engelsizyasam.domain.model.BookModel

@Dao
interface BookDao {

    @Insert
    suspend fun insert(book: BookModel)

    @Update
    suspend fun update(book: BookModel)

    @Query("SELECT * FROM book_table ORDER BY bookId ASC")
    fun getAllBooks(): LiveData<List<BookModel>>

    @Query("SELECT * from book_table WHERE bookId = :key")
    fun getBookWithId(key: Int): LiveData<BookModel>

    @Query("DELETE FROM book_table")
    suspend fun clear()
}