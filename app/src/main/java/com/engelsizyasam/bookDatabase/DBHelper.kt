package com.engelsizyasam.bookDatabase

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class DBHelper(val context: Context) : SQLiteOpenHelper(context,DBHelper.DATABASE_NAME,null,DBHelper.DATABASE_VERSION) {
    private val TABLE_NAME="book_table"
    private val COL_BOOKID = "bookId"
    private val COL_BOOKNAME = "bookName"
    private val COL_BOOKAUTHOR = "bookAuthor"
    private val COL_BOOKIMAGE = "bookImage"
    private val COL_BOOKPDF = "bookPDF"
    companion object {
        private val DATABASE_NAME = "SQLITE_DATABASE"//database adı
        private val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COL_BOOKID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_BOOKNAME  VARCHAR(256),$COL_BOOKAUTHOR  VARCHAR(256),$COL_BOOKIMAGE  VARCHAR(256) NOT NULL UNIQUE, $COL_BOOKPDF  VARCHAR(256))"
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
    }

    fun insertData(books: Books){
        val sqliteDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_BOOKNAME , books.bookName)
        contentValues.put(COL_BOOKAUTHOR, books.bookAuthor)
        contentValues.put(COL_BOOKIMAGE, books.bookImage)
        contentValues.put(COL_BOOKPDF, books.bookPDF)

        val result = sqliteDB.insert(TABLE_NAME,null,contentValues)

       // Toast.makeText(context,if(result != -1L) "Kayıt Başarılı" else "Kayıt yapılamadı.", Toast.LENGTH_SHORT).show()
    }

    fun readData():MutableList<Books>{
        val bookList = mutableListOf<Books>()
        val sqliteDB = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = sqliteDB.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                val books = Books()
                books.bookId = result.getString(result.getColumnIndex(COL_BOOKID)).toInt()
                books.bookName = result.getString(result.getColumnIndex(COL_BOOKNAME))
                books.bookAuthor = result.getString(result.getColumnIndex(COL_BOOKAUTHOR))
                books.bookImage = result.getString(result.getColumnIndex(COL_BOOKIMAGE))
                books.bookPDF = result.getString(result.getColumnIndex(COL_BOOKPDF))//toInt()
                bookList.add(books)
            }while (result.moveToNext())
        }
        result.close()
        sqliteDB.close()
        return bookList
    }
    fun deleteAllData(){
        val sqliteDB = this.writableDatabase
        sqliteDB.delete(TABLE_NAME,null,null)
        sqliteDB.close()

    }

    /*fun updateAge(age:Int) {
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query,null)
        if(result.moveToFirst()){
            do {
                val cv = ContentValues()
                cv.put(COL_AGE,(result.getInt(result.getColumnIndex(COL_AGE))+age))
                db.update(TABLE_NAME,cv, "$COL_ID=? AND $COL_NAME=?",
                    arrayOf(result.getString(result.getColumnIndex(COL_ID)),
                        result.getString(result.getColumnIndex(COL_NAME))))
            }while (result.moveToNext())
        }

        result.close()
        db.close()
    }*/

}