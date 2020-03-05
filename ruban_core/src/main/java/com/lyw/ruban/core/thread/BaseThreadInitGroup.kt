package com.lyw.ruban.core.thread

import com.lyw.ruban.core.BaseInitGroup
import com.lyw.ruban.core.depend.IDependInitObserver

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for base thread init group~
 */
abstract class BaseThreadInitGroup //记录相关的依赖别名～
constructor(var baseThreadGroupCode: Int) : BaseInitGroup<IDependInitObserver>()
    , IThread {
    override fun getThreadCode(): Int {
        return baseThreadGroupCode
    }
}