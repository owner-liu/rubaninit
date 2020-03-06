package com.lyw.ruban.core.thread

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.thread.observer.ThreadGroupObserver

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for thread list~
 */
abstract class AbsThreadList<T : AbsBaseInit<IInitObserver>>
constructor(private var absThreadArrayListCode: Int) :
    AbsThreadInitGroup<IInitObserver>(absThreadArrayListCode) {

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

    override fun initialize(context: InitContext, observer: IInitObserver?) {
        if (hasInit) {
            return
        }

        mObserver.mObserver = observer
        mObserver.mThreadCount = mList?.size ?: 0

        mList?.forEach {
            it.initialize(context, observer)
        }
    }
}