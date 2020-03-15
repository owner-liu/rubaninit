package com.lyw.ruban.init.module

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
    AbsDependModuleInit<ThreadInternalDependArrayList, DependLibInit, IInitObserver>() {

    private val mObserver by lazy { ModuleDependManagerObserver<IInitObserver>(getAliasName()) }

    override var mData: Map<Int, ThreadInternalDependArrayList> = TreeMap()

    override fun put(key: Int, value: ThreadInternalDependArrayList) {
        (mData as TreeMap)[key] = value
    }

    override fun initialize(context: InitContext, observer: IInitObserver) {
        mObserver.libCount = libCount
        super.initialize(context, observer)
    }

    override fun doInit(
        context: InitContext,
        key: Int,
        value: ThreadInternalDependArrayList?,
        observer: IInitObserver
    ) {
        mObserver.mObserver = observer
        value?.initialize(context, mObserver)
    }

    override fun getAliasName(): String {
        return "$moduleCode"
    }

    override fun addInit(init: DependLibInit) {
        val threadCode = init.dependLibInit.libThreadCode
        val threadList = get(threadCode)
        addLib(init, threadList)
    }

    private fun addThreadList(list: ThreadInternalDependArrayList) {
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
        libCount++
        container.commThreadArrayList.add(init)
    }


}
