package com.lyw.ruban.init.widgets

import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.comm.AbsInitArrayList
import com.lyw.ruban.init.lib.LibInit

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for 集合内部相关依赖和完成不进行处理~ 直接外抛~
 */
class LibExternalDependInitArrayList :
    AbsInitArrayList<DependLibInit, IDependInitObserver>() {

    override var mData: List<DependLibInit> = arrayListOf()

    private val mObserverProxy =
        DependProxyObserver<IInitObserver>()

    override fun doInit(
        context: InitContext,
        init: DependLibInit,
        observer: IDependInitObserver
    ) {
        mObserverProxy.mObserver = observer
        init.initialize(context, mObserverProxy)
    }

    override fun getAliasName(): String {
        return this.javaClass.simpleName
    }
}