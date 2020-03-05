package com.lyw.ruban.core.thread

import com.lyw.ruban.core.BaseInit
import com.lyw.ruban.core.depend.IDependInitObserver

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for base thread init～
 */
open class BaseThreadInit
constructor(var baseThreadCode: Int) : BaseInit<IDependInitObserver>()
    , IThread {

    override fun getThreadCode(): Int {
        return baseThreadCode
    }

}