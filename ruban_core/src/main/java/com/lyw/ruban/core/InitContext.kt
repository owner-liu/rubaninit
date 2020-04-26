package com.lyw.ruban.core

import android.app.Application
import android.os.Handler
import android.os.Looper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for init context
 */
open class InitContext
constructor(
    var application: Application? = null,
    var isDebug: Boolean = false
) {
    //异步使用携程～ 方便使用线程池～
    val mInitScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.IO) }

    val syncHandle: Handler by lazy { Handler(Looper.getMainLooper()) }

    var logger: ILogger = Logger(this)
}