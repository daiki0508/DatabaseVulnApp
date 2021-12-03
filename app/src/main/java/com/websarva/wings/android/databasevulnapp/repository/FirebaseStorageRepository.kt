package com.websarva.wings.android.databasevulnapp.repository

import android.content.Context
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.google.gson.Gson
import com.websarva.wings.android.databasevulnapp.model.Users
import java.io.BufferedReader
import java.io.File
import javax.inject.Inject

interface FirebaseStorageRepository {
    suspend fun get(context: Context, listener: (result: Users?) -> Unit)
}

class FirebaseStorageRepositoryClient @Inject constructor(): FirebaseStorageRepository {
    override suspend fun get(context: Context, listener: (result: Users?) -> Unit) {
        // referenceを参照
        val usersRef = Firebase.storage.reference.child("users/personaldata")

        val userFile = File(context.filesDir, "users")
        // fileの存在チェック
        if (userFile.exists()){
            listener(Gson().fromJson(File(context.filesDir, "users").bufferedReader().use(BufferedReader::readText), Users::class.java) as Users)
        }else{
            usersRef.getFile(File(context.filesDir, "users")).addOnSuccessListener {
                Log.i("FirebaseStorage", "Success!")
                listener(Gson().fromJson(File(context.filesDir, "users").bufferedReader().use(BufferedReader::readText), Users::class.java) as Users)
            }.addOnFailureListener {
                Log.e("ERROR", "CANNOT GET FILE.", it)
                listener(null)
            }
        }
    }
}