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

    fun checkLazy(): Boolean {
        return lazy
    }

    fun setLazy(lazy: Boolean) {
        this.lazy = lazy
    }
}