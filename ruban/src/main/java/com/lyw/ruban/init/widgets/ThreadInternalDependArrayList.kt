package com.lyw.ruban.init.widgets

import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInit
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.thread.AbsThreadList
import com.lyw.ruban.init.lib.LibInit

/**
 * Created on  2020-03-07
 * Created by  lyw
 * Created for
 */
class ThreadInternalDependArrayList
constructor(threadCode: Int) : AbsThreadList<LibInit, IInitObserver>(threadCode) {

    override var mData: List<LibInit> = arrayListOf()

    private var mObserver: IInitObserver? = null

    private val mObserverProxy by lazy {
        object : IDependInitObserver<IInitObserver> {
            override fun onCompleted(context: InitContext, aliasName: String) {
                //外抛 成功～
                mObserver?.onCompleted(context, aliasName)
            }

            override fun onWaitToInit(
                context: InitContext,
                init: IInit<IInitObserver>,
                dependAliasName: String
            ) {
                // 内部处理相关队列～
            }

        }

    }

    override fun getAliasName(): String {
        return "ThreadInternalDependArrayList-${getCurrentThreadCode()}"
    }

    override fun add(init: LibInit) {

    }

    override fun doInit(context: InitContext, init: LibInit, observer: IInitObserver) {
        mObserver = observer
        init.initialize(context, mObserverProxy)
    }
}