package com.lyw.ruban.core.thread

import android.os.Looper
import com.lyw.ruban.core.*
import com.lyw.ruban.core.comm.CommStatusObserverInvokeHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Proxy

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for
 */
open class ThreadInitContainer<T : IInitObserver>
constructor(
    threadCode: Int = ConstantsForCore.THREAD_ASYNC,
    var init: AbsBaseInit<T>
) : AbsThreadInit<T>(threadCode), IInit<T> {

    private val mContainerObserver =
        ThreadInitContainerObserver<T>(getCurrentThreadCode())

    override fun getAliasName(): String {
        return init.getAliasName()
    }

    override fun initialize(context: InitContext, observer: T) {
        if (hasInitComplete) {
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
                    if (Looper.myLooper() != Looper.getMainLooper()) {
                        it.initialize(context, proxyObserver)
                    } else {
                        context.mInitScope.launch {
                            withContext(Dispatchers.IO) {
                                it.initialize(context, proxyObserver)
                            }
                        }
                    }
                }

                ConstantsForCore.THREAD_SYNC -> {
                    if (Looper.myLooper() == Looper.getMainLooper()) {
                        it.initialize(context, proxyObserver)
                    } else {
                        context.mInitScope.launch {
                            withContext(Dispatchers.Main) {
                                it.initialize(context, proxyObserver)
                            }
                        }
                    }
                }
                else -> {

                }
            }
        }
    }
}