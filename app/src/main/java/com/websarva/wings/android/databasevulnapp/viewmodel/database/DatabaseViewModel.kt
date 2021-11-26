package com.websarva.wings.android.databasevulnapp.viewmodel.database

import android.app.Application
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.lifecycle.*
import com.websarva.wings.android.databasevulnapp.model.Database
import com.websarva.wings.android.databasevulnapp.repository.DatabaseRepositoryClient
import com.websarva.wings.android.databasevulnapp.viewmodel.SecureSecretKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.inject.Inject
import kotlin.experimental.xor

@HiltViewModel
class DatabaseViewModel @Inject constructor(
    private val databaseRepository: DatabaseRepositoryClient,
    application: Application
) : AndroidViewModel(application) {
    private lateinit var decryptByte: ByteArray
    private lateinit var decryptByte2: ByteArray
    private val _userData = MutableLiveData<Database>()
    val userData: LiveData<Database> = Transformations.map(_userData){
        it
    }

    companion object{
        init {
            System.loadLibrary("database")
        }
    }

    private val somePublicString = "E/AndroidRuntime: FATAL EXCEPTION: main Process: " +
            "com.websarva.wings.android.androidkeystoresample_kotlin, PID: 6147 " +
            "java.lang.RuntimeException"
    private val nonSecret: ByteArray = somePublicString.toByteArray(Charsets.ISO_8859_1)

    fun createDatabase(){
        // 最初の1回のみデータベースを作成する
        if (!File("${getApplication<Application>().applicationContext.filesDir}/../databases/database").exists()){
            decrypt()
            viewModelScope.launch {
                databaseRepository.create(getApplication<Application>().applicationContext, Database(name = String(decryptByte), password = String(decryptByte2)))
                clearBytes()
            }
        }
    }
    fun getData(){
        viewModelScope.launch {
            _userData.value = databaseRepository.read(getApplication<Application>().applicationContext)
        }
    }
    private fun decrypt(){
        Log.d("decrypt", "called")

        var secretKey: SecretKey? = null
        var secretKey2: SecretKey? = null

        try {
            // 複合化処理開始
            secretKey = SecureSecretKey(xorDecode(getAESData(0), 0).toByteArray(), "AES")
            secretKey2 = SecureSecretKey(xorDecode(getAESData(2), 1).toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
            val cipher2 = Cipher.getInstance("AES/ECB/PKCS7Padding")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            cipher2.init(Cipher.DECRYPT_MODE, secretKey2)
            decryptByte = cipher.doFinal(Base64.decode(getAESData(1), Base64.DEFAULT))
            decryptByte2 = cipher.doFinal(Base64.decode(getAESData(3), Base64.DEFAULT))
        } finally {
            // メモリからカギを削除
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                secretKey?.destroy()
                secretKey2?.destroy()
            } else {
                for (i in secretKey!!.encoded.indices) {
                    secretKey.encoded[i] = nonSecret[i % nonSecret.size]
                }
                for (i in secretKey2!!.encoded.indices) {
                    secretKey2.encoded[i] = nonSecret[i % nonSecret.size]
                }
                val out = FileOutputStream("dev/null")
                out.write(secretKey.encoded)
                out.write(secretKey2.encoded)
                out.flush()
                out.close()
            }
        }
    }
    private fun xorDecode(keyPass: String, flag: Int): String {
        val xorDataByte = String(Base64.decode(getXorData(flag), Base64.DEFAULT)).toByteArray(Charsets.US_ASCII)
        var retValue = ""

        for (i in xorDataByte.indices){
            retValue += String(byteArrayOf(String(Base64.decode(keyPass, Base64.DEFAULT)).toByteArray(Charsets.US_ASCII)[i].xor((xorDataByte[i].plus(8)).toByte())))
        }

        return retValue
    }
    private fun clearBytes() {
        // メモリからデータを削除
        for (i in decryptByte.indices) {
            decryptByte[i] = nonSecret[i % nonSecret.size]
        }
        for (i in decryptByte2.indices){
            decryptByte2[i] = nonSecret[i % nonSecret.size]
        }
    }

    private external fun getXorData(flag: Int): String
    private external fun getAESData(flag: Int): String
}