package com.lyw.ruban.core.thread

import android.os.Looper
import android.util.Log
import com.lyw.ruban.core.ConstantsForCore
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IInit
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.CommStatusObserverInvokeHandler
import java.lang.reflect.Proxy

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for
 */
class ThreadInitContainer<T : IInitObserver>
constructor(
    threadCode: Int,
    var init: IInit<T>
) : AbsThreadInit(threadCode), IInit<T> {

    private val mContainerObserver =
        ThreadInitContainerObserver<T>(getCurrentThreadCode())

    override fun getAliasName(): String {
        return javaClass.simpleName
    }

    override fun initialize(context: InitContext, observer: T) {
        if (hasInit) {
            return
        }

        init?.let {
            mContainerObserver.mObserver = observer
            val handler: CommStatusObserverInvokeHandler<T, IInitObserver> =
                CommStatusObserverInvokeHandler(
                    observer,
                    mContainerObserver
                )
            val proxyObserver: T = Proxy.newProxyInstance(
                handler.javaClass.classLoader,
                observer.javaClass.interfaces, handler
            ) as T

            when (getCurrentThreadCode()) {
                ConstantsForCore.THREAD_ASYNC -> {
                    if (context.asyncHandle.looper == Looper.myLooper()) {
                        it.initialize(context, proxyObserver)
                    } else {
                        context.asyncHandle.postAtFrontOfQueue {
                            it.initialize(context, proxyObserver)
                        }
                    }
                }

                ConstantsForCore.THREAD_SYNC -> {
                    if (context.syncHandle.looper == Looper.myLooper()) {
                        init?.initialize(context, proxyObserver)
                    } else {
                        context.syncHandle.postAtFrontOfQueue {
                            it.initialize(context, proxyObserver)
                        }
                    }
                }
                else -> {

                }
            }
        }
    }
}