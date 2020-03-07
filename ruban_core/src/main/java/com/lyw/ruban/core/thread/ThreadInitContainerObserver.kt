package com.lyw.ruban.core.thread

import com.lyw.ruban.core.*


/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for thread container observer~
 */
class ThreadInitContainerObserver<T : IInitObserver>
constructor(
    private var observerThreadCode: Int
) : BaseObserverProxy<T>(),
    IInitObserver {

    override fun onCompleted(context: InitContext, aliasName: String) {
        //保证在主线程回调～
        if (observerThreadCode == ConstantsForCore.THREAD_ASYNC) {
            context.syncHandle.postAtFrontOfQueue {
                mObserver?.onCompleted(context, aliasName)
            }
        } else {
            mObserver?.onCompleted(context, aliasName)
        }

    }
}