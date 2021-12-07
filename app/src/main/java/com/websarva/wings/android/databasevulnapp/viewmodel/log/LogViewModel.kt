package com.websarva.wings.android.databasevulnapp.viewmodel.log

import android.app.Application
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.websarva.wings.android.databasevulnapp.model.Database
import com.websarva.wings.android.databasevulnapp.model.Users
import com.websarva.wings.android.databasevulnapp.viewmodel.SecureSecretKey
import dagger.hilt.android.lifecycle.HiltViewModel
import java.io.FileOutputStream
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.inject.Inject
import kotlin.experimental.xor

@HiltViewModel
class LogViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    private lateinit var decryptByte: ByteArray
    private val _userData = MutableLiveData<Users>()
    val userData: LiveData<Users> = Transformations.map(_userData){
        it
    }

    companion object {
        init {
            System.loadLibrary("Log")
        }
    }

    private val somePublicString = "E/AndroidRuntime: FATAL EXCEPTION: main Process: " +
            "com.websarva.wings.android.androidkeystoresample_kotlin, PID: 6147 " +
            "java.lang.RuntimeException"
    private val nonSecret: ByteArray = somePublicString.toByteArray(Charsets.ISO_8859_1)

    fun decrypt(){
        Log.d("decrypt", "called")

        var secretKey: SecretKey? = null

        try {
            // 複合化処理開始
            secretKey = SecureSecretKey(xorDecode(getAESData(0)).toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            decryptByte = cipher.doFinal(Base64.decode(getAESData(1), Base64.DEFAULT))
            Log.i("flag", String(decryptByte))
            _userData.value = Users(password = String(decryptByte))
        } finally {
            // メモリからカギを削除
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                secretKey?.destroy()
            } else {
                for (i in secretKey!!.encoded.indices) {
                    secretKey.encoded[i] = nonSecret[i % nonSecret.size]
                }
                val out = FileOutputStream("dev/null")
                out.write(secretKey.encoded)
                out.flush()
                out.close()
            }
        }
    }

    private fun xorDecode(keyPass: String): String {
        val xorDataByte = String(Base64.decode(getXorData(), Base64.DEFAULT)).toByteArray(Charsets.US_ASCII)
        var retValue = ""

        for (i in xorDataByte.indices){
            retValue += String(byteArrayOf(String(Base64.decode(keyPass, Base64.DEFAULT)).toByteArray(Charsets.US_ASCII)[i].xor((xorDataByte[i].minus(8)).toByte())))
        }

        return retValue
    }
    private external fun getXorData(): String
    private external fun getAESData(flag: Int): String
}