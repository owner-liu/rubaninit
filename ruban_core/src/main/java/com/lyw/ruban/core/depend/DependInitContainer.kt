package com.lyw.ruban.core.depend

import com.lyw.ruban.core.*
import java.lang.reflect.Proxy

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for depend init container ~
 */
open class DependInitContainer<T : IInitObserver>
constructor(
    aliasList: ArrayList<String>,
    var init: AbsBaseInit<T>
) : AbsDependInit<IDependInitObserver>(aliasList) {

    private val mContainerObserver = DependInitContainerObserver<T>()

    override fun initialize(context: InitContext, observer: IDependInitObserver) {
        if (hasInit) {
            return
        }

        init.let {

            mContainerObserver.mObserver = observer

            if (hasDepend()) {
                //抛出自己～
                mContainerObserver.mObserver?.onWaitToInit(
                    context,
                    this,
                    getFirstDependAlias()
                )
                return
            }

            val handler =
                DependStatusObserverInvokeHandler(
                    observer,
                    mContainerObserver
                )
            val proxyObserver = Proxy.newProxyInstance(
                handler.javaClass.classLoader,
                observer.javaClass.interfaces, handler
            ) as T

            it.initialize(context, proxyObserver)
        }
    }

    override fun getAliasName(): String {
        return this.javaClass.simpleName
    }

}