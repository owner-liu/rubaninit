package com.lyw.ruban.init.liblist

import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.comm.AbsInitArrayList
import com.lyw.ruban.core.depend.DependProxyObserver
import com.lyw.ruban.init.lib.DependThreadLibInit

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for 集合内部相关依赖和完成不进行处理~ 直接外抛~
 */
class ThreadDependLibList :
    AbsInitArrayList<DependThreadLibInit, IDependInitObserver>() {

    override var mData: List<DependThreadLibInit> = arrayListOf()

    private val mObserverProxy =
        DependProxyObserver<IInitObserver>()

    override fun initialize(context: InitContext, observer: IDependInitObserver) {
        // TODO by LYW: 2020/8/27  考虑 拦截 初始化 顺序～ 是否需要从 最大值

        mObserverProxy.mObserver = observer
        super.initialize(context, mObserverProxy)
    }

    override fun doInit(
        context: InitContext,
        init: DependThreadLibInit,
        observer: IDependInitObserver
    ) {
        init.initialize(context, observer)
    }

    override fun getAliasName(): String {
        return this.javaClass.simpleName
    }
}