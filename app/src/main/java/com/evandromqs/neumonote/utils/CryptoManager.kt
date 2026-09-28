package com.evandromqs.neumonote.utils

import android.content.Context
import android.util.Base64
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.security.MessageDigest

class CryptoManager(context: Context) {
    private val preferences by lazy {
        val masterKey = MasterKey.Builder(context.applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context.applicationContext,
            PREFERENCES_FILE,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun salvarSenha(senha: String) {
        val hash = MessageDigest.getInstance("SHA-256")
            .digest(senha.toByteArray(Charsets.UTF_8))

        preferences.edit()
            .putString(PASSWORD_HASH_KEY, Base64.encodeToString(hash, Base64.NO_WRAP))
            .apply()
    }

    fun verificarSenha(senha: String): Boolean {
        val storedHash = preferences.getString(PASSWORD_HASH_KEY, null) ?: return false
        val expectedHash = Base64.decode(storedHash, Base64.NO_WRAP)
        val suppliedHash = MessageDigest.getInstance("SHA-256")
            .digest(senha.toByteArray(Charsets.UTF_8))

        return MessageDigest.isEqual(expectedHash, suppliedHash)
    }

    private companion object {
        const val PREFERENCES_FILE = "neumonote_secure_preferences"
        const val PASSWORD_HASH_KEY = "master_password_sha256"
    }
}