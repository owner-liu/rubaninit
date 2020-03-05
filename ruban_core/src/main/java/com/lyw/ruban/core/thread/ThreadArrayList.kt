package com.lyw.ruban.core.thread

import com.lyw.ruban.core.BaseInitGroup
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.IDependInitObserver

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for thread array list~
 */
class ThreadArrayList
constructor(var threadArrayListCode: Int) : BaseInitGroup<IInitObserver>() {

    override fun isEmpty(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun isNotEmpty(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initialize(context: InitContext, observer: IInitObserver?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}