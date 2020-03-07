package com.lyw.ruban.core

import android.app.Application
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for init context
 */
open class InitContext
constructor(
    var application: Application? = null,
    var isDebug: Boolean = false,
    private var initLoop: Looper? = null
) {
    init {
        val handler = HandlerThread("lib_init")
        handler.start()
        this.initLoop = handler.looper
    }

    val syncHandle: Handler by lazy { Handler(Looper.getMainLooper()) }
    val asyncHandle: Handler by lazy { Handler(initLoop) }
}