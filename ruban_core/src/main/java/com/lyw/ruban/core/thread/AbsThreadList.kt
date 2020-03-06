package com.lyw.ruban.core.thread

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.thread.observer.ThreadGroupObserver

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for abs thread list~
 */
abstract class AbsThreadList<T : AbsBaseThreadInit>
constructor(private var absThreadArrayListCode: Int) : AbsThreadInitGroup(absThreadArrayListCode) {

    private val mList: List<T>? = null

    override fun isEmpty(): Boolean {
        return !isNotEmpty()
    }

    override fun isNotEmpty(): Boolean {
        return mList?.isNotEmpty() == true
    }

    private val mObserver by lazy {
        ThreadGroupObserver(
            absThreadArrayListCode,
            this
        )
    }


    final override fun initialize(context: InitContext, observer: IInitObserver?) {
        if (hasInit) {
            return
        }

        mObserver.mObserver = observer
        mObserver.mThreadCount = mList?.size ?: 0

        mList?.forEach {
            doInit(context, it, mObserver)
        }
    }

    abstract fun doInit(context: InitContext, value: T?, observer: IInitObserver?)
}