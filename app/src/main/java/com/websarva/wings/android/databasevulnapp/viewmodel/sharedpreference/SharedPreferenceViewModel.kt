package com.websarva.wings.android.databasevulnapp.viewmodel.sharedpreference

import android.app.Application
import android.os.Build
import android.util.Base64
import android.util.Log
import androidx.lifecycle.*
import com.websarva.wings.android.databasevulnapp.model.RetPreference
import com.websarva.wings.android.databasevulnapp.repository.SharedPreferenceRepositoryClient
import com.websarva.wings.android.databasevulnapp.viewmodel.SecureSecretKey
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.util.*
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.inject.Inject
import javax.security.auth.Destroyable
import kotlin.experimental.xor

@HiltViewModel
class SharedPreferenceViewModel @Inject constructor(
    private val sharedPreferenceRepository: SharedPreferenceRepositoryClient,
    application: Application
) : AndroidViewModel(application) {
    private lateinit var decryptByte: ByteArray
    private val _password = MutableLiveData<RetPreference>()
    val password: LiveData<RetPreference> = Transformations.map(_password){
        it
    }

    companion object{
        init {
            System.loadLibrary("shared_preference")
        }
    }

    private val somePublicString = "E/AndroidRuntime: FATAL EXCEPTION: main Process: " +
            "com.websarva.wings.android.androidkeystoresample_kotlin, PID: 6147 " +
            "java.lang.RuntimeException"
    private val nonSecret: ByteArray = somePublicString.toByteArray(Charsets.ISO_8859_1)

    fun createPreference(){
        // 最初の1回のみsharedPreferenceに値を書き込む
        if (!File("${getApplication<Application>().applicationContext.filesDir}/../shared_prefs/Key.xml").exists()){
            decrypt()
            viewModelScope.launch {
                sharedPreferenceRepository.write(getApplication<Application>().applicationContext, String(decryptByte))
                clearBytes()
            }
        }
    }
    fun getPassword(){
        viewModelScope.launch {
            _password.value = sharedPreferenceRepository.read(getApplication<Application>().applicationContext)
        }
    }
    private fun decrypt() {
        Log.d("decrypt", "called")

        var secretKey: SecretKey? = null

        try {
            // 複合化処理開始
            secretKey = SecureSecretKey(xorDecode(getAESData(0)).toByteArray(), "AES")
            val cipher = Cipher.getInstance("AES/ECB/PKCS7Padding")
            cipher.init(Cipher.DECRYPT_MODE, secretKey)
            decryptByte = cipher.doFinal(Base64.decode(getAESData(1), Base64.DEFAULT))
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
            retValue += String(byteArrayOf(String(Base64.decode(keyPass, Base64.DEFAULT)).toByteArray(Charsets.US_ASCII)[i].xor((xorDataByte[i].minus(29)).toByte())))
        }

        return retValue
    }
    private fun clearBytes() {
        // メモリからデータを削除
        for (i in decryptByte.indices) {
            decryptByte[i] = nonSecret[i % nonSecret.size]
        }
    }

    private external fun getAESData(flag: Int): String
    private external fun getXorData(): String
}