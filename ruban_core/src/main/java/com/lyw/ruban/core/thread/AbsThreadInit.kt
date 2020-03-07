package com.lyw.ruban.core.thread

import com.lyw.ruban.core.AbsInit

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for
 */
abstract class AbsThreadInit
constructor(private var threadCode: Int) : AbsInit()
    , IThread {

    override fun getCurrentThreadCode(): Int {
        return threadCode
    }
}