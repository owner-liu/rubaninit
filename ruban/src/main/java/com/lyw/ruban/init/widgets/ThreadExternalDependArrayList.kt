package com.lyw.ruban.init.widgets

import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.DependInitContainer
import com.lyw.ruban.init.lib.LibInit

/**
 * Created on  2020-03-07
 * Created by  lyw
 * Created for
 */
class ThreadExternalDependArrayList
//constructor(threadCode: Int) :
//    AbsThreadExternalDependList<DependInitContainer<LibInit>>(threadCode) {
//
//    override fun initList(): List<DependInitContainer<LibInit>> {
//        return arrayListOf()
//    }
//
//    override fun add(init: DependInitContainer<LibInit>) {
//        (getData() as ArrayList).add(init)
//    }
//
//    override fun doInit(
//        context: InitContext,
//        init: DependInitContainer<LibInit>,
//        observer: IDependInitObserverOptimize<DependInitContainer<LibInit>>
//    ) {
//        init.initialize(context, observer)
//    }
//
//    override fun getAliasName(): String {
//        return "ThreadExternal_${getCurrentThreadCode()}"
//    }
//}