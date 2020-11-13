package com.lyw.ruban.core.thread

import android.os.Looper
import com.lyw.ruban.core.*
import kotlinx.coroutines.launch

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
        ThreadInitContainerObserver<T>(this)

    override fun getAliasName(): String {
        return init.getAliasName()
    }

    override fun initialize(context: InitContext, observer: T) {
        context.logger.i(msg = "threadContainer-init:${init.getAliasName()}")

        if (checkInitStart()) {
            context.logger.i(msg = "init cancel:${getAliasName()} for ThreadInitContainer initialize ")
            return
        }

        startInit()

        init?.let {
            mContainerObserver.mObserver = observer
            val proxyObserver = mContainerObserver as T

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