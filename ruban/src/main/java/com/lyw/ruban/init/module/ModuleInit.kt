package com.lyw.ruban.init.module

import com.lyw.ruban.core.*
import com.lyw.ruban.core.thread.ThreadInitContainer
import com.lyw.ruban.init.lib.LibInit
import com.lyw.ruban.init.widgets.CommThreadArrayList
import com.lyw.ruban.init.widgets.LibInitArrayList
import java.util.*

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for module~ 不涉及任何依赖～ 只包含线程～
 * 内部格式 ： Map<Int, ThreadInitContainer<LibInitArrayList<LibInit>>>
 *
 */
class ModuleInit
constructor(
    var moduleCode: Int
) : IInitMap<Int, CommThreadArrayList, IInitObserver>,
    AbsBaseInit<IInitObserver>(),
    IModule<CommThreadArrayList, LibInit> {

    override var mData: Map<Int, CommThreadArrayList> = TreeMap()


    override fun put(key: Int, value: CommThreadArrayList) {
        (getData() as TreeMap)[key] = value
    }

    override fun doInit(
        context: InitContext,
        key: Int,
        value: CommThreadArrayList?,
        observer: IInitObserver
    ) {
        value?.initialize(context, observer)
    }


    override fun getAliasName(): String {
        return "Module-$moduleCode"
    }

    override fun addInit(init: LibInit) {
        val threadCode = init.libThreadCode
        val threadList = get(threadCode)
        addLib(init, threadList)
    }

    override fun addThreadList(list: CommThreadArrayList) {
        put(list.getCurrentThreadCode(), list)
    }

    private fun addLib(
        init: LibInit,
        threadInitContainer: ThreadInitContainer<IInitObserver>?
    ) {
        val threadCode = init.libThreadCode
        var container = threadInitContainer ?: let {
            val list = LibInitArrayList()
            CommThreadArrayList(threadCode, list).also {
                addThreadList(it)
            }
        }

        (container.init as LibInitArrayList).add(init)
    }

}