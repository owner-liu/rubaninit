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
        // TODO by LYW: 2020/11/6 需要增加判断是否完成，需要通过动态代理来做～
//        if (checkInitFinished()) {
//            return
//        }

        init.let {
            init.initialize(context, observer)
        }
    }

    override fun initialize(context: InitContext, observer: T) {
        if (checkLazy()) {
            context.logger.i(msg = "initialize-lazy-init:${getAliasName()}")
            return
        }
        initializeLazy(context, observer)
    }
}