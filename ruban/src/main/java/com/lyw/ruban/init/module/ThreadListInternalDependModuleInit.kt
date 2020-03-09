package com.lyw.ruban.init.module

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IInitMap
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.widgets.DependLibInit
import com.lyw.ruban.init.widgets.ThreadInternalDependArrayList
import java.util.*

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for internal module init ~
 */
class ThreadListInternalDependModuleInit
constructor(
    private var moduleCode: Int
) : IInitMap<Int, ThreadInternalDependArrayList, IInitObserver>,
    AbsModuleInit<ThreadInternalDependArrayList, DependLibInit, IInitObserver>() {

    override var mData: Map<Int, ThreadInternalDependArrayList> = TreeMap()

    override fun put(key: Int, value: ThreadInternalDependArrayList) {
        (mData as TreeMap)[key] = value
    }

    override fun doInit(
        context: InitContext,
        key: Int,
        value: ThreadInternalDependArrayList?,
        observer: IInitObserver
    ) {
        value?.initialize(context, observer)
    }

    override fun getAliasName(): String {
        return "ThreadInternalDependModuleInit-$moduleCode"
    }

    override fun addInit(init: DependLibInit) {
        val threadCode = init.dependLibInit.libThreadCode
        val threadList = get(threadCode)
        addLib(init, threadList)
    }

    override fun addThreadList(list: ThreadInternalDependArrayList) {
        put(list.getCurrentThreadCode(), list)
    }

    private fun addLib(
        init: DependLibInit,
        threadInitContainer: ThreadInternalDependArrayList?
    ) {
        val threadCode = init.dependLibInit.libThreadCode
        var container = threadInitContainer ?: let {
            ThreadInternalDependArrayList(threadCode).also {
                addThreadList(it)
            }
        }
        container.commThreadArrayList.add(init)
    }


}
