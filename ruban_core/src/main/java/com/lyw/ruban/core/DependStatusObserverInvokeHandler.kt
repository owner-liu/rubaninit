package com.lyw.ruban.core

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for simple status observer invoke handler~
 */
class DependStatusObserverInvokeHandler
constructor(
    var sourceObserver: IDependInitObserver,
    var statusObserver: IDependInitObserver
) : InvocationHandler {

    override fun invoke(p0: Any?, p1: Method, p2: Array<out Any>?): Any? {

        return when (p1.name) {
            "onCompleted" -> {
                p1.invoke(statusObserver, *(p2 ?: emptyArray()))
            }

            "onWaitToInit" -> {
                p1.invoke(statusObserver, *(p2 ?: emptyArray()))
            }
            else -> {
                p1.invoke(sourceObserver, *(p2 ?: emptyArray()))
            }
        }
    }
}