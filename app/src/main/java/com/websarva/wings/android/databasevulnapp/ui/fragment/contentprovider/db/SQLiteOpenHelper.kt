package com.websarva.wings.android.databasevulnapp.ui.fragment.contentprovider.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.websarva.wings.android.databasevulnapp.model.ColumnName

class SQLiteOpenHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "content_provider01.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        // テーブルの作成
        db?.execSQL("CREATE TABLE IF NOT EXISTS users (${ColumnName._id.name} INTEGER PRIMARY KEY, ${ColumnName.Username.name} VARCHAR NOT NULL, ${ColumnName.Password.name} VARCHAR NOT NULL);")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        Log.w("SQLiteOpenHelper", "onUpgrade Called.")
    }
}