package com.lyw.ruban.core.thread

import android.os.Looper
import com.lyw.ruban.core.*


/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for thread container observer~
 */
class ThreadInitContainerObserver<T : IInitObserver> : BaseObserverProxy<T>(),
    IInitObserver {

    override fun onCompleted(context: InitContext, aliasName: String) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            mObserver?.onCompleted(context, aliasName)
        } else {
            context.syncHandle.postAtFrontOfQueue {
                mObserver?.onCompleted(context, aliasName)
            }
        }
    }
}