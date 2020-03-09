package com.lyw.ruban.core.thread

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IInitObserver

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for
 */
abstract class AbsThreadInit<T : IInitObserver>
constructor(private var threadCode: Int) : AbsBaseInit<T>()
    , IThread {

    override fun getCurrentThreadCode(): Int {
        return threadCode
    }
}