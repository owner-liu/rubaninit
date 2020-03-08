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
 * Created for module 内，线程列表间无依赖～ 库之间存在依赖，并且依赖是线程内部依赖～
 */
class ThreadInternalDependModuleInit
constructor(
    var moduleCode: Int
) : IInitMap<Int, ThreadInternalDependArrayList, IInitObserver>,
    AbsBaseInit<IInitObserver>(),
    IModule<ThreadInternalDependArrayList, DependLibInit> {

    override var mData: Map<Int, ThreadInternalDependArrayList> = TreeMap()

    override fun put(key: Int, value: ThreadInternalDependArrayList) {
        (mData as TreeMap).put(key, value)
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
