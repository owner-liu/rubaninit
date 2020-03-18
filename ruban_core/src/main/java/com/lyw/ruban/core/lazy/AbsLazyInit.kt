package com.lyw.ruban.core.lazy

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for abs base depend init~
 */
abstract class AbsLazyInit<T : IInitObserver>
constructor(private var lazy: Boolean = false) :
    AbsBaseInit<T>(),
    ILazyInit<T> {

    val mLock = Any()

    //是否已经触发过 lazy 检测～ 主要涉及 初始化 lazy module， 是否需要主动调用 initialize～
    var hasCheckLazy = false

    fun checkLazy(): Boolean {
        return lazy
    }

    fun setLazy(lazy: Boolean) {
        this.lazy = lazy
    }
}