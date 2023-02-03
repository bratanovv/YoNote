package com.example.zametki.db

import android.provider.BaseColumns

object MyDbNameClass : BaseColumns {
    const val TABLE_NAME = "my_table"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_CONTENT = "content"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME = "Zametki.db"

    const val CREATE_TABLE  = "CREATE TABLE $TABLE_NAME (" +
                                "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                                "$COLUMN_NAME_TITLE TEXT," +
                                "$COLUMN_NAME_CONTENT TEXT)"
    const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}