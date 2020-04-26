package com.lyw.ruban.core

import android.util.Log

/**
 * Created on  2020-04-26
 * Created by  lyw
 * Created for logger~
 */
class Logger
constructor(val context: InitContext) : ILogger {

    override fun d(tag: String, msg: String) {
        if (context.isDebug) {
            Log.d(tag, msg)
        }
    }

    override fun e(tag: String, msg: String) {
        if (context.isDebug) {
            Log.e(tag, msg)
        }
    }

    override fun i(tag: String, msg: String) {
        if (context.isDebug) {
            Log.i(tag, msg)
        }
    }

    override fun v(tag: String, msg: String) {
        if (context.isDebug) {
            Log.v(tag, msg)
        }
    }

    override fun w(tag: String, msg: String) {
        if (context.isDebug) {
            Log.w(tag, msg)
        }
    }
}


interface ILogger {
    fun d(tag: String = "ruban", msg: String)
    fun e(tag: String = "ruban", msg: String)
    fun i(tag: String = "ruban", msg: String)
    fun v(tag: String = "ruban", msg: String)
    fun w(tag: String = "ruban", msg: String)
}