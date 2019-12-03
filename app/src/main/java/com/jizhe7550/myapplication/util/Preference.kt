package com.jizhe7550.myapplication.util

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.jizhe7550.myapplication.app.App
import java.io.*
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty


class Preference<T>(private val name: String, private val default: T) : ReadWriteProperty<Any?, T> {

    companion object {
        const val Has_cache = "has_cache"
    }

    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(App.CONTEXT)
    }

    override fun getValue(thisRef: Any?, property: KProperty<*>): T {
        return getValue(name, default)
    }

    override fun setValue(thisRef: Any?, property: KProperty<*>, value: T) {
        putValue(name, value)
    }

    @SuppressLint("CommitPrefEdits")
    private fun <T> putValue(name: String, value: T) = with(prefs.edit()) {
        when (value) {
            is Long -> putLong(name, value)
            is String -> putString(name, value)
            is Int -> putInt(name, value)
            is Boolean -> putBoolean(name, value)
            is Float -> putFloat(name, value)
            else -> putString(name, serialize(value))
        }.apply()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getValue(name: String, default: T): T = with(prefs) {
        val res: Any = when (default) {
            is Long -> getLong(name, default)
            is String -> getString(name,default)!!
            is Int -> getInt(name, default)
            is Boolean -> getBoolean(name, default)
            is Float -> getFloat(name, default)
            else -> deSerialization(getString(name, serialize(default))!!)
        }
        return res as T
    }

    /**
     * delete all
     */
    fun clearPreference() {
        prefs.edit().clear().apply()
    }

    /**
     * delete data by key
     */
    fun clearPreference(key: String) {
        prefs.edit().remove(key).apply()
    }

    /**
     * Serialization
     * @param person
     * *
     * @return
     * *
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun <A> serialize(obj: A): String {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val objectOutputStream = ObjectOutputStream(
                byteArrayOutputStream)
        objectOutputStream.writeObject(obj)
        var serStr = byteArrayOutputStream.toString("ISO-8859-1")
        serStr = java.net.URLEncoder.encode(serStr, "UTF-8")
        objectOutputStream.close()
        byteArrayOutputStream.close()
        return serStr
    }

    /**
     * deSerialization
     * @param str
     * *
     * @return
     * *
     * @throws IOException
     * *
     * @throws ClassNotFoundException
     */
    @Suppress("UNCHECKED_CAST")
    @Throws(IOException::class, ClassNotFoundException::class)
    private fun <A> deSerialization(str: String): A {
        val redStr = java.net.URLDecoder.decode(str, "UTF-8")
        val byteArrayInputStream = ByteArrayInputStream(
                redStr.toByteArray(charset("ISO-8859-1")))
        val objectInputStream = ObjectInputStream(
                byteArrayInputStream)
        val obj = objectInputStream.readObject() as A
        objectInputStream.close()
        byteArrayInputStream.close()
        return obj
    }


    /**
     * find a key
     *
     * @param key
     * @return
     */
    fun contains(key: String): Boolean {
        return prefs.contains(key)
    }

    /**
     * get All key-values
     *
     * @param context
     * @return
     */
    fun getAll(): Map<String, *> {
        return prefs.all
    }
}