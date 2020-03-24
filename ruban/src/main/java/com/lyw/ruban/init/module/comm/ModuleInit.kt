package com.lyw.ruban.init.module.comm

import com.lyw.ruban.core.*
import com.lyw.ruban.init.lib.LibInit
import com.lyw.ruban.init.module.depend.ModuleDependManagerObserver
import com.lyw.ruban.init.widgets.comm.CommThreadArrayList
import java.util.*

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for module~ 不涉及任何依赖～ 只包含线程～
 * 内部格式 ： Map<Int, CommThreadArrayList>
 *
 */
class ModuleInit
constructor(
    var moduleCode: Int
) : IInitMap<Int, CommThreadArrayList, IInitObserver>,
    AbsModuleInit<CommThreadArrayList, LibInit, IInitObserver>() {

    private val mObserver by lazy {
        ModuleManagerObserver(getAliasName())
    }

    override var mData: Map<Int, CommThreadArrayList> = TreeMap()

    override fun put(key: Int, value: CommThreadArrayList) {
        (getData() as TreeMap)[key] = value
    }

    override fun initialize(context: InitContext, observer: IInitObserver) {
        mObserver.mObserver = observer
        mObserver.initCount = initCount
        super.initialize(context, mObserver)
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
        return "$moduleCode"
    }

    override fun addInit(init: LibInit) {
        val threadCode = init.libThreadCode
        val threadList = get(threadCode)
        addLib(init, threadList)
    }

    private fun addThreadList(list: CommThreadArrayList) {
        put(list.getCurrentThreadCode(), list)
    }

    private fun addLib(
        init: LibInit,
        threadInitContainer: CommThreadArrayList?
    ) {
        val threadCode = init.libThreadCode
        var container = threadInitContainer ?: let {
            CommThreadArrayList(threadCode).also {
                addThreadList(it)
            }
        }
        container.commThreadArrayList.add(init)
    }

}