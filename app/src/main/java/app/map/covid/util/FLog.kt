package app.map.covid.util

import android.util.Log
import app.map.covid.BuildConfig

object FLog {
    val DEBUG: Boolean = BuildConfig.DEBUG
    const val LENGTH: Int = 1024

    fun v(tag: String, message: Any) {
        if (DEBUG) Log.v(tag, message.toString())
    }

    fun d(tag: String, message: Any) {
        if (DEBUG) Log.d(tag, message.toString())
    }

    fun i(tag: String, message: Any) {
        if (DEBUG) Log.i(tag, message.toString())
    }

    fun w(tag: String, message: Any) {
        if (DEBUG) Log.w(tag, message.toString())
    }

    fun e(tag: String, message: Any) {
        if (DEBUG) {
            var temp = message.toString()
            while (temp.isNotEmpty()) {
                if (temp.length > LENGTH) {
                    Log.e(tag, temp.substring(0, LENGTH))
                    temp = temp.substring(LENGTH)
                } else {
                    Log.e(tag, temp)
                    break
                }
            }
        }
    }

    fun Class<*>.e(message: Any) {
        if (DEBUG) Log.e(this.simpleName, message.toString())
    }

    fun w(clas: Class<*>, message: Any) {
        if (DEBUG) Log.w(clas.simpleName, message.toString())
    }

    fun i(clas: Class<*>, message: Any) {
        if (DEBUG) Log.i(clas.simpleName, message.toString())
    }

    fun d(clas: Class<*>, message: Any) {
        if (DEBUG) Log.d(clas.simpleName, message.toString())
    }

    fun v(clas: Class<*>, message: Any) {
        if (DEBUG) Log.v(clas.simpleName, message.toString())
    }
}