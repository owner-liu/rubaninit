package com.lyw.ruban.init.module

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IInitMap
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.DependInitContainer
import com.lyw.ruban.core.thread.ThreadInitContainer
import com.lyw.ruban.init.lib.LibInit
import java.util.*

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for module 内，线程列表间无依赖～ 库之间存在依赖，并且依赖是线程内部依赖～
 */
class ThreadInternalDependModuleInit
constructor(
    var moduleCode: Int
) : IInitMap<Int, ThreadInitContainer<IInitObserver>, IInitObserver>,
    AbsBaseInit<IInitObserver>(),
    IModule<ThreadInitContainer<IInitObserver>, DependInitContainer<IInitObserver>> {

    override var mData: Map<Int, ThreadInitContainer<IInitObserver>> = TreeMap()

    override fun put(key: Int, value: ThreadInitContainer<IInitObserver>) {
        (getData() as TreeMap)[key] = value
    }

    override fun doInit(
        context: InitContext,
        key: Int,
        value: ThreadInitContainer<IInitObserver>?,
        observer: IInitObserver
    ) {
        value?.initialize(context, observer)
    }

    override fun getAliasName(): String {
        return "ThreadInternalDependModule-$moduleCode"
    }

    override fun addInit(init: DependInitContainer<IInitObserver>) {
        // TODO by LYW: 2020-03-08 处理相关逻辑～
    }

    fun addInit(init: LibInit) {
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
        // TODO by LYW: 2020-03-08 LibInitArrayList 进行替换～
//        val threadCode = init.libThreadCode
//        var container = threadInitContainer ?: let {
//            val list = LibInitArrayList()
//            ThreadInitContainer(threadCode, list).also {
//                addThreadList(it)
//            }
//        }
//
//        (container.init as LibInitArrayList).add(init)
    }

}
