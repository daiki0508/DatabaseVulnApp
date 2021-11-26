package com.websarva.wings.android.databasevulnapp.viewmodel

import java.io.FileOutputStream
import java.util.*
import javax.crypto.SecretKey
import javax.security.auth.Destroyable

class SecureSecretKey(key2: ByteArray, algorithm: String): SecretKey, Destroyable {
    private var key: ByteArray?
    private val algorithm: String

    override fun getAlgorithm(): String {
        return algorithm
    }

    override fun getFormat(): String {
        return "RAW"
    }

    override fun getEncoded(): ByteArray {
        if (key == null){
            throw NullPointerException()
        }
        return key!!.clone()
    }

    override fun destroy() {
        if (isDestroyed){
            return
        }
        val dummy = "E/AndroidRuntime: FATAL EXCEPTION: main Process: " +
                "com.websarva.wings.android.androidkeystoresample_kotlin, PID: 6147 " +
                "java.lang.RuntimeException"
        val nonSecret: ByteArray = dummy.toByteArray(Charsets.ISO_8859_1)

        for (i in key!!.indices){
            key!![i] = nonSecret[i % nonSecret.size]
        }
        val out= FileOutputStream("/dev/null")
        out.write(key)
        out.flush()
        out.close()
        key = null
        System.gc()
    }

    override fun isDestroyed(): Boolean {
        return key == null
    }

    init {
        this.key = key2.clone()
        this.algorithm = algorithm
        Arrays.fill(key2, 0)
    }
}