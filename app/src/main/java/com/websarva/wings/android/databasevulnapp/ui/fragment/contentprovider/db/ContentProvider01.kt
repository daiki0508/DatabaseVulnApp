package com.websarva.wings.android.databasevulnapp.ui.fragment.contentprovider.db

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri

class ContentProvider01: ContentProvider() {
    private lateinit var helper: SQLiteOpenHelper
    private var sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    companion object {
        private const val USERS = 1
        private const val USERS_ID = 2
    }

    override fun onCreate(): Boolean {
        // instanceの作成
        helper = SQLiteOpenHelper(context!!)
        // uriホワイトリストを定義
        sUriMatcher.addURI("com.websarva.wings.android.databasevulnapp.ui.fragment.contentprovider.db.ContentProvider01", "Users", USERS)

        return true
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val queryBuilder = SQLiteQueryBuilder()
        // uriチェック
        when(sUriMatcher.match(uri)){
            USERS, USERS_ID -> queryBuilder.tables = "users"
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        return queryBuilder.query(helper.readableDatabase, projection, selection, selectionArgs, null, null, sortOrder)
    }

    override fun getType(p0: Uri): String? {
        // MiMEタイプのリクエスト
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val insertTable: String
        val contentUri: Uri?

        // uriチェック
        when(sUriMatcher.match(uri)){
            USERS -> {
                insertTable = "users"
                contentUri = uri
            }
            USERS_ID -> throw IllegalArgumentException("Invalid URI, cannot insert with ID: $uri")
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        val rowId = helper.writableDatabase.insert(insertTable, null, values)
        if (rowId > 0){
            val returnUri = ContentUris.withAppendedId(contentUri, rowId)
            context?.contentResolver?.notifyChange(returnUri, null)
            return returnUri
        }else{
            throw IllegalArgumentException("Failed to insert row into: $uri")
        }
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        TODO("Not yet implemented")
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        TODO("Not yet implemented")
    }
}