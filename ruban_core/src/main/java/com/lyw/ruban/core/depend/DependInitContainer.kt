package com.lyw.ruban.core.depend

import android.util.Log
import com.lyw.ruban.core.*
import java.lang.reflect.Proxy

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for depend init container ~
 */
open class DependInitContainer<T : IInitObserver>
constructor(
    aliasList: ArrayList<String> = arrayListOf(),
    var init: AbsBaseInit<T>
) : AbsDependInit<IDependInitObserver>(aliasList) {

    private val mContainerObserver =
        DependProxyObserver<T>()

    override fun getAliasName(): String {
        return init.getAliasName()
    }

    override fun initialize(context: InitContext, observer: IDependInitObserver) {
        Log.i("ruban_test_depend", "${init.getAliasName()},hasDepend:${hasDepend()}")
        if (hasInitComplete) {
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
            )

            it.initialize(context, proxyObserver as T)
        }
    }
}