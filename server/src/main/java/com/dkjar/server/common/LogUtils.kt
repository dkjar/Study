package com.dkjar.server.common

import android.util.Log
import com.dkjar.server.BuildConfig

object LogUtils {
    val HANDLER = "HANDLER"

    private val PREFIX = "S-"

    fun isDebug(): Boolean {
        return BuildConfig.API_ENV
    }

    fun i(tag: String, message: String?) {
        if (isDebug()) {
            message?.let {
                Log.i(PREFIX + tag, it)
            }
        }
    }

    fun v(tag: String, message: String?) {
        if (isDebug()) {
            message?.let { Log.v(PREFIX + tag, it) }
        }
    }

    fun safeCheckCrash(tag: String, msg: String, tr: Throwable?) {
        if (isDebug()) {
            throw RuntimeException("$PREFIX$tag $msg", tr)
        } else {
            Log.e(PREFIX + tag, msg, tr)
        }
    }

    fun e(tag: String?, msg: String?, tr: Throwable?) {
        Log.e(tag, msg, tr)
    }

    fun e(tag: String, message: String?) {
        if (isDebug()) {
            message?.let { Log.e(PREFIX + tag, it) }
        }
    }

}