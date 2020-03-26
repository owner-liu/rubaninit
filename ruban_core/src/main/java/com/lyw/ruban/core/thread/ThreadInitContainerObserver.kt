package com.lyw.ruban.core.thread

import com.lyw.ruban.core.*


/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for thread container observer~
 */
class ThreadInitContainerObserver<T : IInitObserver>
constructor(
    private var observerThreadCode: Int = ConstantsForCore.THREAD_ASYNC
) : BaseObserverProxy<T>(),
    IInitObserver {

    override fun onCompleted(context: InitContext, aliasName: String) {
        if (observerThreadCode == ConstantsForCore.THREAD_ASYNC) {
            context.syncHandle.postAtFrontOfQueue {
                mObserver?.onCompleted(context, aliasName)
            }
        } else {
            mObserver?.onCompleted(context, aliasName)
        }
    }
}