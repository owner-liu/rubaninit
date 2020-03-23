package com.lyw.ruban.core

import android.app.Application
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
    val mInitScope: CoroutineScope by lazy { CoroutineScope(Dispatchers.Main) }
}