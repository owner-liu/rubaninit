package com.lyw.ruban.init.widgets

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.comm.AbsInitArrayList
import com.lyw.ruban.core.comm.DependManagerObserver

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for 集合内部 存在依赖～
 */
class LibInternalDependInitArrayList :
    AbsInitArrayList<DependLibInit, IInitObserver>() {

    override var mData: List<DependLibInit> = arrayListOf()

    private val mObserverPoxy by lazy {
        DependManagerObserver<IInitObserver>()
    }

    override fun doInit(context: InitContext, init: DependLibInit, observer: IInitObserver) {
        mObserverPoxy.mObserver = observer
        init.initialize(context, mObserverPoxy)
    }

    override fun getAliasName(): String {
        return javaClass.simpleName
    }
}
