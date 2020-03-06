package com.lyw.ruban.core.thread.observer

import com.lyw.ruban.core.*
import com.lyw.ruban.core.thread.AbsBaseThreadInit
import com.lyw.ruban.core.thread.AbsThreadList

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for thread container observer~
 */
class ThreadGroupObserver<T : AbsBaseThreadInit>
constructor(
    private var observerThreadCode: Int,
    private var threadInit: AbsThreadList<T>
) : IInitObserver {

    var mObserver: IInitObserver? = null

    //所有的数目～
    var mThreadCount: Int = 0

    //已初始化～
    private var mInitCompletedAliases = arrayListOf<String>()

    override fun onCompleted(context: InitContext, aliasName: String) {

        mInitCompletedAliases.add(threadInit.getAliasName())

        if (mInitCompletedAliases.size == mThreadCount) {
            threadInit.hasInit = true

            if (observerThreadCode == ConstantsForCore.THREAD_ASYNC) {
                context.syncHandle?.postAtFrontOfQueue {
                    mObserver?.onCompleted(context, threadInit.getAliasName())
                }
            } else {
                mObserver?.onCompleted(context, threadInit.getAliasName())
            }
        }
    }
}