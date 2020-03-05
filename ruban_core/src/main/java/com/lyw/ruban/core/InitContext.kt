package com.lyw.ruban.core

import android.app.Application
import android.os.Handler
import android.os.Looper

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for init context
 */
open class InitContext {
    var application: Application? = null
    var isDebug: Boolean = false
    var initLoop: Looper? = null
    var syncHandle: Handler? = null
    var asyncHandle: Handler? = null
}