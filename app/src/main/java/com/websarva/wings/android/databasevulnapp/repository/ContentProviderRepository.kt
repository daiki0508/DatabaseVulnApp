package com.websarva.wings.android.databasevulnapp.repository

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import com.websarva.wings.android.databasevulnapp.model.ColumnName
import javax.inject.Inject

interface ContentProviderRepository {
    suspend fun insert(context: Context)
}

class ContentProviderRepositoryClient @Inject constructor(): ContentProviderRepository {
    override suspend fun insert(context: Context) {
        setData(context, "dXNlcg==", "VGhpc19pc191c2VyX3BhMzN3MHJkLg==")
        setData(context, "cm9vdA==", "UjAwdF91c2VyX2kzXzN0cjBuZy4=")
        setData(context, "ZGFpa2kwNTA4", "ZGFpa2kwNTA4e1RoZXJlX2kzX24wX2ZsYWdfZjByX3RoaTNfcHIwYjFlbS5d")
    }

    private fun setData(context: Context, name: String, password: String){
        with(ContentValues()){
            put(ColumnName.Username.name, name)
            put(ColumnName.Password.name, password)
            context.contentResolver.insert(Uri.parse("content://com.websarva.wings.android.databasevulnapp.ui.fragment.contentprovider.db.ContentProvider01/Users"), this)
        }
    }
}