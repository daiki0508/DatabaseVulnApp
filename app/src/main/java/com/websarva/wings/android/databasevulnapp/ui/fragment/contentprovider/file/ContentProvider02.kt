package com.websarva.wings.android.databasevulnapp.ui.fragment.contentprovider.file

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.content.res.AssetFileDescriptor
import android.database.Cursor
import android.net.Uri
import android.util.Log
import java.io.FileNotFoundException

class ContentProvider02: ContentProvider() {
    private var sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
    companion object {
        private const val FILE = 100
    }

    override fun onCreate(): Boolean {
        // uriホワイトリストを定義
        sUriMatcher.addURI("com.websarva.wings.android.databasevulnapp.ui.fragment.contentprovider.file.ContentProvider02", "*", FILE)

        return false
    }

    override fun openAssetFile(uri: Uri, mode: String): AssetFileDescriptor? {
        // ファイルの取得
        return try {
            context!!.assets.openFd(uri.pathSegments.joinToString("/"))
        }catch (e: FileNotFoundException){
            Log.e("ERROR", "Unable to open file: ${e.message}")
            null
        }
    }

    override fun query(
        p0: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        return null
    }

    override fun getType(p0: Uri): String? {
        return null
    }

    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }
}