package com.lyw.ruban.init.module.depend

import com.lyw.ruban.core.*
import com.lyw.ruban.init.widgets.depend.DependThreadLibInit
import com.lyw.ruban.init.widgets.depend.ThreadExternalDependArrayList
import com.lyw.ruban.init.widgets.thread.ThreadLibInit
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
    AbsDependModuleInit<ThreadExternalDependArrayList, DependThreadLibInit, IDependInitObserver>() {

    private val mObserver by lazy {
        ModuleDependManagerObserver<IDependInitObserver>(
            getAliasName(),
            this
        )
    }

    override var mData: Map<Int, ThreadExternalDependArrayList> = TreeMap()

    override fun put(key: Int, value: ThreadExternalDependArrayList) {
        (mData as TreeMap)[key] = value
    }

    override fun initialize(context: InitContext, observer: IDependInitObserver) {
        if (hasInitComplete) {
            return
        }
        mObserver.initCount = initCount
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
        return "$moduleCode"
    }

    override fun addInit(init: DependThreadLibInit) {
        hasInitComplete = false

        val threadCode = (init.init as ThreadLibInit).getCurrentThreadCode()
        val threadList = get(threadCode)
        addLib(init, threadList)
    }

    private fun addThreadList(list: ThreadExternalDependArrayList) {
        put(list.getCurrentThreadCode(), list)
    }

    private fun addLib(
        init: DependThreadLibInit,
        threadInitContainer: ThreadExternalDependArrayList?
    ) {
        val threadCode = (init.init as ThreadLibInit).getCurrentThreadCode()
        var container = threadInitContainer ?: let {
            ThreadExternalDependArrayList(
                threadCode
            ).also {
                addThreadList(it)
            }
        }
        initCount++
        container.commThreadArrayList.add(init)
    }


}
