package com.lyw.ruban.core.thread.observer

import com.lyw.ruban.core.*
import com.lyw.ruban.core.thread.ThreadInitContainer

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for thread init observer~
 */
class ThreadContainerObserver<T : AbsBaseInit<IInitObserver>>
constructor(
    private var observerThreadCode: Int,
    private var container: ThreadInitContainer<T>,
    private var init: T
) : IInitObserver {

    var mObserver: IInitObserver? = null

    override fun onCompleted(context: InitContext, aliasName: String) {

        container.hasInit = true
        init.hasInit = true

        //保证在主线程回调～
        if (observerThreadCode == ConstantsForCore.THREAD_ASYNC) {
            context.syncHandle?.postAtFrontOfQueue {
                mObserver?.onCompleted(context, init.getAliasName())
            }
        } else {
            mObserver?.onCompleted(context, init.getAliasName())
        }
    }
}