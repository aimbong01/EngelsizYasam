package com.engelsizyasam.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

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

@Database(entities = [BookModel::class], version = 1, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract val bookDatabaseDao: BookDatabaseDao

    companion object {
        @Volatile
        private var INSTANCE: BookDatabase? = null

        fun getInstance(context: Context): BookDatabase {

            synchronized(this) {

                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(context.applicationContext, BookDatabase::class.java, "sleep_history_database")
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
