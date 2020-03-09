package com.lyw.ruban.init.widgets

import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.comm.AbsInitArrayList
import com.lyw.ruban.init.lib.LibInit

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for 集合内的lib库初始化 依赖于 另一个集合内的lib初始化～
 */
class LibExternalDependInitArrayList :
    AbsInitArrayList<LibInit, IDependInitObserver<IInitObserver>>() {

    override var mData: List<LibInit> = arrayListOf()

    override fun doInit(
        context: InitContext,
        init: LibInit,
        observer: IDependInitObserver<IInitObserver>
    ) {
        init.initialize(context, observer)
    }

    override fun getAliasName(): String {
        return this.javaClass.simpleName
    }
}