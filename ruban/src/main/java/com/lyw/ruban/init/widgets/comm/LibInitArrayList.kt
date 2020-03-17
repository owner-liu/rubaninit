package com.lyw.ruban.init.widgets.comm

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.comm.AbsInitArrayList
import com.lyw.ruban.init.lib.LibInit

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for lib init ArrayList~
 */
class LibInitArrayList : AbsInitArrayList<LibInit, IInitObserver>() {

    override var mData: List<LibInit> = arrayListOf()

    override fun doInit(context: InitContext, init: LibInit, observer: IInitObserver) {
        init.initialize(context, observer)
    }

    override fun getAliasName(): String {
        return javaClass.simpleName
    }
}