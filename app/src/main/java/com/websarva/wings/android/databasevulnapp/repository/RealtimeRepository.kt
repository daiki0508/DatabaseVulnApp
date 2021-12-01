package com.websarva.wings.android.databasevulnapp.repository

import android.util.Log
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.websarva.wings.android.databasevulnapp.model.Database
import javax.inject.Inject

interface RealtimeRepository {
    suspend fun getData(listener: (result: Database?) -> Unit)
}

class RealTimeRepositoryClient @Inject constructor(): RealtimeRepository {
    override suspend fun getData(listener: (result: Database?) -> Unit) {
        // databaseReferenceを取得
        val database = Firebase.database.reference

        // データを取得
        database.child("users").child("2").get().addOnSuccessListener {
            listener(Database(name = it.child("name").value.toString(), password = it.child("password").value.toString()))
        }.addOnFailureListener {
            Log.e("ERROR", "CANNOT GET DATA.", it)
            listener(null)
        }
    }
}