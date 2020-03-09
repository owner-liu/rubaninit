package com.lyw.ruban.init.app

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IInitMap
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.lib.ILibOperation
import com.lyw.ruban.init.lib.LibInit
import com.lyw.ruban.init.module.ModuleInit
import com.lyw.ruban.init.widgets.CommThreadArrayList
import java.util.*

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for app 相关库不存在依赖～
 */
class AppInit
    : IInitMap<Int, ModuleInit, IInitObserver>,
    AbsBaseInit<IInitObserver>(),
    ILibOperation {

    override var mData: Map<Int, ModuleInit> = TreeMap()

    override fun put(key: Int, value: ModuleInit) {
        (mData as TreeMap).put(key, value)
    }

    override fun doInit(
        context: InitContext,
        key: Int,
        value: ModuleInit?,
        observer: IInitObserver
    ) {
        value?.initialize(context, observer)
    }

    override fun getAliasName(): String {
        return javaClass.simpleName
    }

    override fun addLibInit(init: LibInit) {
        val moduleCode = init.libModuleCode
        val module = mData.get(moduleCode) ?: let {
            ModuleInit(moduleCode).also {
                put(moduleCode, it)
            }
        }
        module.addInit(init)
    }
}