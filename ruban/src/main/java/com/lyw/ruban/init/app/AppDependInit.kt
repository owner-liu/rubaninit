package com.lyw.ruban.init.app

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitMap
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.comm.DependManagerObserver
import com.lyw.ruban.core.depend.DependInitContainer
import com.lyw.ruban.init.lib.IDependLibOperation
import com.lyw.ruban.init.lib.LibInit
import com.lyw.ruban.init.module.ThreadListExternalDependModuleInit
import com.lyw.ruban.init.widgets.DependLibInit
import java.util.*

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for app module 存在依赖，初始化库存在module内的依赖～
 */
class AppDependInit
    : IInitMap<Int, DependInitContainer<IDependInitObserver>, IDependInitObserver>,
    AbsBaseInit<IDependInitObserver>(),
    IDependLibOperation {

    private var mManagerObserver = DependManagerObserver<IDependInitObserver>()

    override var mData: Map<Int, DependInitContainer<IDependInitObserver>> = TreeMap()

    override fun put(key: Int, value: DependInitContainer<IDependInitObserver>) {
        (mData as TreeMap).put(key, value)
    }

    override fun doInit(
        context: InitContext,
        key: Int,
        value: DependInitContainer<IDependInitObserver>?,
        observer: IDependInitObserver
    ) {
        mManagerObserver.mObserver = observer
        value?.initialize(context, mManagerObserver)
    }

    override fun getAliasName(): String {
        return javaClass.simpleName
    }

    override fun addDependLibInit(init: DependLibInit) {
        val moduleCode = init.dependLibInit.libModuleCode
        val module = mData[moduleCode] ?: let {
            DependInitContainer(
                arrayListOf(),
                ThreadListExternalDependModuleInit(moduleCode)
            ).also {
                put(moduleCode, it)
            }
        }

        (module.init as ThreadListExternalDependModuleInit).addInit(init)
    }

    override fun addLibInit(libInit: LibInit) {
        val dependAliases = libInit.libDependAlias
        val dependContainer = DependLibInit(dependAliases, libInit)
        addDependLibInit(dependContainer)
    }
}