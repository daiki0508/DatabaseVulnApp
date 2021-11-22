package com.websarva.wings.android.databasevulnapp.repository

import android.content.Context
import com.websarva.wings.android.databasevulnapp.model.FileName
import com.websarva.wings.android.databasevulnapp.model.KeyName
import com.websarva.wings.android.databasevulnapp.model.RetPreference
import javax.inject.Inject

interface SharedPreferenceRepository {
    suspend fun write(context: Context, password: String)
    suspend fun read(context: Context): RetPreference
}

class SharedPreferenceRepositoryClient @Inject constructor(): SharedPreferenceRepository {
    override suspend fun write(context: Context, password: String) {
        with(context.getSharedPreferences(FileName.Key.name, Context.MODE_PRIVATE).edit()){
            putString(KeyName.Password.name, password)
            commit()
        }
    }

    override suspend fun read(context: Context): RetPreference {
        return RetPreference(password = context.getSharedPreferences(FileName.Key.name,Context.MODE_PRIVATE)?.getString(KeyName.Password.name, "")!!)
    }
}