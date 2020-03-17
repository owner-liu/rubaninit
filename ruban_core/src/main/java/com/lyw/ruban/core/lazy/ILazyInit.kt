package com.lyw.ruban.core.lazy

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext

/**
 * Created on  2020-03-17
 * Created by  lyw
 * Created for lazy init~
 */
interface ILazyInit<T : IInitObserver> {
    fun initializeLazy(context: InitContext, observer: T)
}