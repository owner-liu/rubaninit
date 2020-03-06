package com.lyw.ruban.core.thread

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.AbsInitGroup

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for abs thread init group~
 */
abstract class AbsThreadInitGroup<T:IInitObserver>
//记录相关的依赖别名～
constructor(
    private var absThreadInitGroupCode: Int
) : AbsInitGroup<T>(),
    IThread {

    override fun getThreadCode(): Int {
        return absThreadInitGroupCode
    }
}