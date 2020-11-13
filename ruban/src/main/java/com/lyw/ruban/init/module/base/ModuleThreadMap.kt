package com.lyw.ruban.init.module.base

import com.lyw.ruban.ICompleteListener
import com.lyw.ruban.IInitCompleteObserverOperate
import com.lyw.ruban.core.*
import com.lyw.ruban.init.lib.DependThreadLibInit
import com.lyw.ruban.init.liblist.ThreadDependLibList
import com.lyw.ruban.init.lib.ThreadLibInit
import java.util.*

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created external module init~
 */
class ModuleThreadMap
constructor(
    var moduleCode: Int
) : IInitMap<Int, ThreadDependLibList, IDependInitObserver>,
    AbsModuleThreadInit<ThreadDependLibList,
            DependThreadLibInit, IDependInitObserver>(),
    IInitCompleteObserverOperate {

    private val mObserver by lazy {
        ModuleThreadMapObserver(
            this
        )
    }

    override var mData: Map<Int, ThreadDependLibList> = TreeMap()

    override fun put(key: Int, value: ThreadDependLibList) {
        (mData as TreeMap)[key] = value
    }

    override fun initialize(context: InitContext, observer: IDependInitObserver) {
        if (status != ConstantsForCore.INIT_STATUS_DEFAULT) {
            context.logger.i(msg = "init cancel:${getAliasName()} for ModuleThreadMap initialize ")
            return
        }
        status = ConstantsForCore.INIT_STATUS_INITING

        mObserver.mObserver = observer
        mObserver.initCount = initCount
        super.initialize(context, mObserver)
    }

    override fun doInit(
        context: InitContext,
        key: Int,
        value: ThreadDependLibList?,
        observer: IDependInitObserver
    ) {
        value?.initialize(context, observer)
    }

    override fun getAliasName(): String {
        return "$moduleCode"
    }

    override fun addInit(init: DependThreadLibInit) {
        status = ConstantsForCore.INIT_STATUS_DEFAULT

        val threadCode = (init.init as ThreadLibInit).getCurrentThreadCode()
        val threadList = get(threadCode)
        addLib(init, threadList)
    }

    private fun addThreadList(threadCode: Int, list: ThreadDependLibList) {
        put(threadCode, list)
    }

    private fun addLib(
        init: DependThreadLibInit,
        threadInitContainer: ThreadDependLibList?
    ) {
        val threadCode = (init.init as ThreadLibInit).getCurrentThreadCode()
        var container = threadInitContainer ?: let {

            // LABEL BY LYW: 同个module，针对于 线程 再分类，添加到 不同的 LibExternalDependInitArrayList中～
            ThreadDependLibList().also {
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
