package com.lyw.ruban.init.module.depend

import com.lyw.ruban.ICompleteListener
import com.lyw.ruban.IInitCompleteObserverOperate
import com.lyw.ruban.core.*
import com.lyw.ruban.init.widgets.depend.DependThreadLibInit
import com.lyw.ruban.init.widgets.depend.LibExternalDependInitArrayList
import com.lyw.ruban.init.widgets.thread.ThreadLibInit
import java.util.*

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created external module init~
 */
class ModuleLibExternalDependMap
constructor(
    var moduleCode: Int
) : IInitMap<Int, LibExternalDependInitArrayList, IDependInitObserver>,
    AbsDependModuleInit<LibExternalDependInitArrayList,
            DependThreadLibInit, IDependInitObserver>(),
    IInitCompleteObserverOperate {

    private val mObserver by lazy {
        ModuleDependManagerObserver(getAliasName())
    }

    override var mData: Map<Int, LibExternalDependInitArrayList> = TreeMap()

    override fun put(key: Int, value: LibExternalDependInitArrayList) {
        (mData as TreeMap)[key] = value
    }

    override fun initialize(context: InitContext, observer: IDependInitObserver) {
        if (hasInitComplete) {
            return
        }
        mObserver.mObserver = observer
        mObserver.initCount = initCount
        super.initialize(context, mObserver)
    }

    override fun doInit(
        context: InitContext,
        key: Int,
        value: LibExternalDependInitArrayList?,
        observer: IDependInitObserver
    ) {
        value?.initialize(context, observer)
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

    private fun addThreadList(threadCode: Int, list: LibExternalDependInitArrayList) {
        put(threadCode, list)
    }

    private fun addLib(
        init: DependThreadLibInit,
        threadInitContainer: LibExternalDependInitArrayList?
    ) {
        val threadCode = (init.init as ThreadLibInit).getCurrentThreadCode()
        var container = threadInitContainer ?: let {
            LibExternalDependInitArrayList().also {
                addThreadList(threadCode, it)
            }
        }
        initCount++

        container.add(init)
    }


    override fun addInitCompletedListener(
        moduleCode: Int,
        InitAliasName: String,
        listener: ICompleteListener
    ) {
        mObserver.addInitCompletedListener(moduleCode, InitAliasName, listener)
    }

}
