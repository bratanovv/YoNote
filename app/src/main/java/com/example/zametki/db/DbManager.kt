package com.example.zametki.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns

class DbManager(context: Context) {

    val dbHelper = DbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDb() {
        db = dbHelper.writableDatabase

    }

    fun insertToDb(title: String, desc: String, star: Int, pass: String, date: String) {
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)    //name
            put(MyDbNameClass.COLUMN_NAME_CONTENT, desc)   //description/content
            put(MyDbNameClass.COLUMN_NAME_STAR, star)      //marcked/unmarcked
            put(MyDbNameClass.COLUMN_NAME_PASS, pass)      //pass
            put(MyDbNameClass.COLUMN_NAME_DATE, date)
        }
        db?.insert(MyDbNameClass.TABLE_NAME, null, values)
    }

    fun removeItemFromDb(id: String) {
        val selection = BaseColumns._ID + "=$id"

        db?.delete(MyDbNameClass.TABLE_NAME, selection, null)
    }

    fun updateItemtoDb(
        title: String,
        desc: String,
        star: Int,
        pass: String,
        date: String,
        id: String
    ) {
        val values = ContentValues().apply {
            put(MyDbNameClass.COLUMN_NAME_TITLE, title)    //name
            put(MyDbNameClass.COLUMN_NAME_CONTENT, desc)   //description/content
            put(MyDbNameClass.COLUMN_NAME_STAR, star)      //marcked/unmarcked
            put(MyDbNameClass.COLUMN_NAME_PASS, pass)      //pass
            put(MyDbNameClass.COLUMN_NAME_DATE, date)
        }
        val selection = BaseColumns._ID + "=$id"
        db?.update(MyDbNameClass.TABLE_NAME, values, selection, null)
    }

    fun readDbData(searchText: String, marcked: Boolean): ArrayList<DbItem> {
        val dataList = ArrayList<DbItem>()
        val selection = "${MyDbNameClass.COLUMN_NAME_TITLE} like ? "

        val cursor = db?.query(
            MyDbNameClass.TABLE_NAME,     // The table to query
            null,                 // The array of columns to return (pass null to get all)
            selection,                    // The columns for the WHERE clause
            arrayOf("%$searchText%"),     // The values for the WHERE clause
            null,                 // don't group the rows
            null,                  // don't filter by row groups
            MyDbNameClass.COLUMN_NAME_DATE + " DESC"          // The sort order
        )


        while (cursor?.moveToNext()!!) {

            val dataTitle =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_TITLE))
                    .toString()
            val dataContent =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_CONTENT))
                    .toString()
            val dataStar =
                cursor.getInt(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_STAR))
            val dataPass =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_PASS))
                    .toString()
            val dataDate =
                cursor.getString(cursor.getColumnIndexOrThrow(MyDbNameClass.COLUMN_NAME_DATE))
                    .toString()
            val dataId = cursor.getInt(cursor.getColumnIndexOrThrow(BaseColumns._ID))
            if ((marcked && dataStar == 1) || !marcked)
                dataList.add(DbItem(dataId, dataTitle, dataContent, dataStar, dataPass, dataDate))

        }

        cursor.close()
        return dataList
    }

    fun closeDB() {
        dbHelper.close()
    }
}