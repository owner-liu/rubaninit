package com.lyw.ruban.core.thread

import android.os.Looper
import android.util.Log
import com.lyw.ruban.core.*
import com.lyw.ruban.core.comm.CommStatusObserverInvokeHandler
import kotlinx.coroutines.launch
import java.lang.reflect.Proxy

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for thread container～
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

        Log.i("ruban", "threadContainer-init:${init.getAliasName()}")
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
                            it.initialize(context, proxyObserver)
                        }
                    }
                }

                ConstantsForCore.THREAD_SYNC -> {
                    if (Looper.myLooper() == Looper.getMainLooper()) {
                        it.initialize(context, proxyObserver)
                    } else {
                        context.syncHandle.post {
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