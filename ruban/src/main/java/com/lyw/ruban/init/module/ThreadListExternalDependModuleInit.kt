package com.lyw.ruban.init.module

import com.lyw.ruban.core.*
import com.lyw.ruban.init.widgets.DependLibInit
import com.lyw.ruban.core.comm.DependManagerObserver
import com.lyw.ruban.init.widgets.ThreadExternalDependArrayList
import java.util.*

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created external module init~
 */
class ThreadListExternalDependModuleInit
constructor(
    var moduleCode: Int
) : IInitMap<Int, ThreadExternalDependArrayList, IDependInitObserver>,
    AbsDependModuleInit<ThreadExternalDependArrayList, DependLibInit, IDependInitObserver>() {

    private val mObserver by lazy { ModuleDependManagerObserver<IDependInitObserver>(getAliasName()) }

    override var mData: Map<Int, ThreadExternalDependArrayList> = TreeMap()

    override fun put(key: Int, value: ThreadExternalDependArrayList) {
        (mData as TreeMap)[key] = value
    }

    override fun initialize(context: InitContext, observer: IDependInitObserver) {
        mObserver.libCount = libCount
        super.initialize(context, observer)
    }

    override fun doInit(
        context: InitContext,
        key: Int,
        value: ThreadExternalDependArrayList?,
        observer: IDependInitObserver
    ) {
        mObserver.mObserver = observer
        value?.initialize(context, mObserver)
    }

    override fun getAliasName(): String {
        return "${javaClass.simpleName}-$moduleCode"
    }

    override fun addInit(init: DependLibInit) {
        val threadCode = init.dependLibInit.libThreadCode
        val threadList = get(threadCode)
        addLib(init, threadList)
    }

    private fun addThreadList(list: ThreadExternalDependArrayList) {
        put(list.getCurrentThreadCode(), list)
    }

    private fun addLib(
        init: DependLibInit,
        threadInitContainer: ThreadExternalDependArrayList?
    ) {
        val threadCode = init.dependLibInit.libThreadCode
        var container = threadInitContainer ?: let {
            ThreadExternalDependArrayList(threadCode).also {
                addThreadList(it)
            }
        }
        libCount++
        container.commThreadArrayList.add(init)
    }


}
