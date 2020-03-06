package com.lyw.ruban.core.thread

import android.os.Looper
import com.lyw.ruban.core.*
import com.lyw.ruban.core.thread.observer.ThreadContainerObserver

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for thread init proxy~
 */
open class ThreadInitContainer<T : AbsBaseInit<IInitObserver>>
constructor(
    private var baseThreadCode: Int,
    private var init: T? = null
) : AbsBaseThreadInit(baseThreadCode) {


    private val mObserver by lazy {
        init?.let {
            ThreadContainerObserver(baseThreadCode, this, it)
        } ?: throw IllegalArgumentException("ThreadInitContainer  init is null")
    }

    override fun doInit(context: InitContext, observer: IInitObserver?) {

        if (hasInit) {
            return
        }

        init?.let {
            mObserver.mObserver = observer

            when (baseThreadCode) {
                ConstantsForCore.THREAD_ASYNC -> {
                    if (context.asyncHandle?.looper == Looper.myLooper()) {
                        init?.initialize(context, mObserver)
                    } else {
                        context.asyncHandle?.postAtFrontOfQueue {
                            it.initialize(context, mObserver)
                        }
                    }

                }

                ConstantsForCore.THREAD_SYNC -> {
                    if (context.syncHandle?.looper == Looper.myLooper()) {
                        init?.initialize(context, mObserver)
                    } else {
                        context.syncHandle?.postAtFrontOfQueue {
                            it.initialize(context, mObserver)
                        }
                    }
                }
                else -> {

                }
            }
        }
    }

    override fun getAliasName(): String {
        return init?.getAliasName() ?: this.javaClass.simpleName
    }
}