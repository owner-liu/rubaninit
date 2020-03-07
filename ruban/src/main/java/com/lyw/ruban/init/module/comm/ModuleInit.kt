package com.lyw.ruban.init.module.comm

import android.util.Log
import com.lyw.ruban.core.*
import com.lyw.ruban.core.comm.AbsInitTreeMap
import com.lyw.ruban.core.thread.ThreadInitContainer
import com.lyw.ruban.init.lib.LibInit
import com.lyw.ruban.init.module.IModule
import com.lyw.ruban.init.widgets.LibInitArrayList
import java.util.*

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for module~ 不涉及任何依赖～ 只包含线程～
 * 内部格式 ： Map<Int, ThreadInitContainer<InitArrayList<LibInit>>>
 *
 */
class ModuleInit
constructor(
    var moduleCode: Int
) : IInitMap<Int, ThreadInitContainer<IInitObserver>, IInitObserver>,
    AbsBaseInit<IInitObserver>(),
    IModule<ThreadInitContainer<IInitObserver>, LibInit> {

    override var mData: Map<Int, ThreadInitContainer<IInitObserver>> = TreeMap()

    override fun getAliasName(): String {
        return "module-$moduleCode"
    }

    override fun put(key: Int, value: ThreadInitContainer<IInitObserver>) {
        (getData() as TreeMap)[key] = value
    }

    override fun get(key: Int): ThreadInitContainer<IInitObserver>? {
        return (getData() as TreeMap)[key]
    }

    override fun doInit(
        context: InitContext,
        key: Int,
        value: ThreadInitContainer<IInitObserver>?,
        observer: IInitObserver
    ) {
        value?.initialize(context, observer)
    }

    override fun addInit(init: LibInit) {
        val threadCode = init.libThreadCode
        val threadList = get(threadCode)
        addLib(init, threadList)
    }

    override fun addThreadList(list: ThreadInitContainer<IInitObserver>) {
        put(list.getCurrentThreadCode(), list)
    }

    private fun addLib(
        init: LibInit,
        threadInitContainer: ThreadInitContainer<IInitObserver>?
    ) {
        val threadCode = init.libThreadCode
        var container = threadInitContainer ?: let {
            val list = LibInitArrayList()
            ThreadInitContainer(threadCode, list).also {
                addThreadList(it)
            }
        }

        (container.init as LibInitArrayList).add(init)
    }

}