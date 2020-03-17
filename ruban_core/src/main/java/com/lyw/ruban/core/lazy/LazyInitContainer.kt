package com.lyw.ruban.core.lazy

import com.lyw.ruban.core.*

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for depend init container ~
 */
open class LazyInitContainer<T : IInitObserver>
constructor(
    lazy: Boolean = false,
    var init: AbsBaseInit<T>
) : AbsLazyInit<T>(lazy) {

    override fun getAliasName(): String {
        return init.getAliasName()
    }

    override fun initializeLazy(context: InitContext, observer: T) {
        if (hasInitComplete) {
            return
        }

        init.let {
            init.initialize(context, observer)
        }
    }

    override fun initialize(context: InitContext, observer: T) {
        if (checkLazy()) {
            return
        }
        initializeLazy(context, observer)
    }
}