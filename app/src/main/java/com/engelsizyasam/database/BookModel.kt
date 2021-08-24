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

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents one night's sleep through start, end times, and the sleep quality.
 */
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
        var bookPDF: String = "")