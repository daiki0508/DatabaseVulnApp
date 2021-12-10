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
    suspend fun getEx(context: Context, listener: (result: Users?) -> Unit)
    suspend fun getUrlFile(context: Context, listener: (result: Boolean) -> Unit)
}

class FirebaseStorageRepositoryClient @Inject constructor(): FirebaseStorageRepository {
    override suspend fun get(context: Context, listener: (result: Users?) -> Unit) {
        // referenceを参照
        val usersRef = Firebase.storage.reference.child("users/personaldata")

        val userFile = File(context.filesDir, "users")
        // fileの存在チェック
        if (userFile.exists()){
            listener(Gson().fromJson(userFile.bufferedReader().use(BufferedReader::readText), Users::class.java) as Users)
        }else{
            usersRef.getFile(userFile).addOnSuccessListener {
                Log.i("FirebaseStorage", "Success!")
                listener(Gson().fromJson(userFile.bufferedReader().use(BufferedReader::readText), Users::class.java) as Users)
            }.addOnFailureListener {
                Log.e("ERROR", "CANNOT GET FILE.", it)
                listener(null)
            }
        }
    }

    override suspend fun getEx(context: Context, listener: (result: Users?) -> Unit) {
        // referenceを参照
        val usersRef = Firebase.storage.reference.child("users/personaldataex")

        val userExFile = File(context.getExternalFilesDir(null), "usersex")
        // fileの存在チェック
        if (userExFile.exists()){
            listener(Gson().fromJson(userExFile.bufferedReader().use(BufferedReader::readText), Users::class.java) as Users)
        }else{
            usersRef.getFile(userExFile).addOnSuccessListener {
                Log.i("FirebaseStorage", "Success!")
                listener(Gson().fromJson(userExFile.bufferedReader().use(BufferedReader::readText), Users::class.java) as Users)
            }.addOnFailureListener {
                Log.e("ERROR", "CANNOT GET FILE.", it)
                listener(null)
            }
        }
    }

    override suspend fun getUrlFile(context: Context, listener: (result: Boolean) -> Unit) {
        // referenceを参照
        val urlsRef = Firebase.storage.reference.child("url/load_url")

        val urlsFile = File(context.filesDir, "load_url")
        // fileの存在チェック
        if (urlsFile.exists()){
            listener(true)
        }else{
            urlsRef.getFile(urlsFile).addOnSuccessListener {
                Log.i("FirebaseStorage", "Success!")
                listener(true)
            }.addOnFailureListener{
                Log.e("ERROR", "CANNOT GET FILE.", it)
                listener(false)
            }
        }
    }
}