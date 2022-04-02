package com.simplecalculator.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.annotation.Nullable
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.reflect.TypeToken
import com.simplecalculator.GsonUtils
import java.lang.reflect.Type

class DefaultLocalStorageImpl(appContext: Context) : LocalStorage {

    private val sharedPreferences: SharedPreferences

    init {
        sharedPreferences = getEncryptedPrefs(appContext)
    }

    override fun storeInt(key: String, value: Int) {
        sharedPreferences
            .edit()
            .putInt(key, value)
            .apply()
    }

    override fun storeLong(key: String, value: Long) {
        sharedPreferences
            .edit()
            .putLong(key, value)
            .apply()
    }

    override fun <T> storeToList(key: String, value: T) {
        getList<List<T>>(key)?.let {
            val list = ArrayList(it[0]).apply { add(value) }
            sharedPreferences
                .edit()
                .putString(key, GsonUtils.toJson(list))
                .apply()
        } ?: run {
            storeList<T>(key, listOf(value))
        }
    }

    override fun <T> storeObject(key: String, value: T) {
        sharedPreferences
            .edit()
            .putString(key, GsonUtils.toJson(value))
            .apply()
    }

    override fun <T> storeList(key: String, value: List<T>) {
        sharedPreferences
            .edit()
            .putString(key, GsonUtils.toJson(value))
            .apply()
    }

    override fun storeFloat(key: String, value: Float) {
        sharedPreferences
            .edit()
            .putFloat(key, value)
            .apply()
    }

    override fun storeString(key: String, value: String) {
        sharedPreferences
            .edit()
            .putString(key, value)
            .apply()
    }

    override fun storeBoolean(key: String, value: Boolean) {
        sharedPreferences
            .edit()
            .putBoolean(key, value)
            .apply()
    }

    override fun <T> getObject(key: String): T? {
        return sharedPreferences.getString(key, null)?.let { json ->
            GsonUtils.gson.fromJson(json, object : TypeToken<T>() {}.type)
        }
    }

    override fun <T> getList(key: String): List<T>? {
        return sharedPreferences.getString(key, "")?.let {
            GsonUtils.gson.fromJson(it, object : TypeToken<List<T>>() {}.type)
        }
    }

    override fun getInt(key: String) = sharedPreferences.getInt(key, 0)

    override fun getLong(key: String) = sharedPreferences.getLong(key, 0L)

    override fun getFloat(key: String) = sharedPreferences.getFloat(key, 0F)

    override fun getString(key: String) = sharedPreferences.getString(key, null) ?: ""

    override fun getBoolean(key: String) = sharedPreferences.getBoolean(key, false)

    override fun clear() {
        sharedPreferences
            .edit()
            .clear()
            .apply()
    }

    private fun getEncryptedPrefs(context: Context): SharedPreferences {
        val key = MasterKey
            .Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return EncryptedSharedPreferences.create(
            context,
            "com.implementhing",
            key,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}