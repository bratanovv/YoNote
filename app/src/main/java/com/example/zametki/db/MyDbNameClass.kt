package com.example.zametki.db

import android.provider.BaseColumns

object MyDbNameClass : BaseColumns {
    const val TABLE_NAME = "my_table"
    const val COLUMN_NAME_TITLE = "title"
    const val COLUMN_NAME_CONTENT = "content"
    const val COLUMN_NAME_STAR = "star"
    const val COLUMN_NAME_PASS = "pass"
    const val COLUMN_NAME_DATE = "date"

    const val DATABASE_VERSION = 3
    const val DATABASE_NAME = "Zametki.db"

    const val CREATE_TABLE  = "CREATE TABLE $TABLE_NAME (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "$COLUMN_NAME_TITLE TEXT," +
            "$COLUMN_NAME_CONTENT TEXT," +
            "$COLUMN_NAME_STAR INTEGER," +
            "$COLUMN_NAME_PASS TEXT," +
            "$COLUMN_NAME_DATE TEXT)"

    const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"

    const val SORT_TABLE = "Select * from $TABLE_NAME order by $COLUMN_NAME_DATE"
}