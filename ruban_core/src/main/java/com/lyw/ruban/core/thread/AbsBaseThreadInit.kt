package com.lyw.ruban.core.thread

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IInitObserver

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for base thread init～
 */
abstract class AbsBaseThreadInit
constructor(private var absBaseThreadCode: Int) : AbsBaseInit<IInitObserver>()
    , IThread {

    override fun getThreadCode(): Int {
        return absBaseThreadCode
    }
}