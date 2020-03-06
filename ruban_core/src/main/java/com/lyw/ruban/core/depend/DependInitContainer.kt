package com.lyw.ruban.core.depend

import com.lyw.ruban.core.*
import com.lyw.ruban.core.depend.observer.DependContainerObserver

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for depend init container~
 */
open class DependInitContainer<T : AbsBaseInit<IInitObserver>>
constructor(
    private var dependInitProxyAliases: ArrayList<String>,
    private var init: T?
) : AbsBaseDependInit(dependInitProxyAliases) {

    private val mObserver by lazy {
        init?.let {
            DependContainerObserver(this, it)
        } ?: throw IllegalArgumentException("DependInitContainer  init is null")
    }

    override fun doInit(context: InitContext, observer: IDependInitObserver?) {
        if (hasInit) {
            return
        }

        init?.let {

            mObserver.mObserver = observer

            if (hasDepend()) {
                observer?.waitToInit(context, this, getFirstDependAlias())
                return
            }

            //可以执行，则执行对应的数据～
            it.initialize(context, mObserver)
        }
    }

    override fun getAliasName(): String {
        return init?.getAliasName() ?: this.javaClass.simpleName
    }
}