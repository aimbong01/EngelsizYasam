package com.engelsizyasam.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "book_table", indices = arrayOf(Index(value = ["book_image"], unique = true)))
data class BookModel(
    @PrimaryKey(autoGenerate = true)
    var bookId: Int = 0,

    @ColumnInfo(name = "book_name")
    var bookName: String = "",

    @ColumnInfo(name = "book_author")
    var bookAuthor: String = "",

    @ColumnInfo(name = "book_image")
    var bookImage: String = "",

    @ColumnInfo(name = "book_pdf")
    var bookPDF: String = "",

    @ColumnInfo(name = "book_voice_url")
    var bookVoiceUrl: String = "",

    @ColumnInfo(name = "book_page")
    var bookPage: Int = 0,

    @ColumnInfo(name = "book_page_size")
    var bookPageSize: String = ""

)