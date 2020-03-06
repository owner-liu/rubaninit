package com.lyw.ruban.init.module

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.AbsDependMap
import com.lyw.ruban.core.thread.AbsThreadList
import com.lyw.ruban.init.lib.LibInit
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for module~ 涉及线程～
 */
class ModuleInit
constructor(
    var moduleCode: Int,
    var moduleAlias: ArrayList<String>
) : AbsDependMap<Int, AbsThreadList<LibInit>>(moduleAlias),
    IModule {

    init {
        mData = TreeMap()
    }

    override fun doInit(
        context: InitContext,
        key: Int,
        value: AbsThreadList<LibInit>?,
        observer: IInitObserver?
    ) {
        value?.initialize(context, observer)
    }

    override fun getAliasName(): String {
        return moduleCode.toString()
    }

    override fun addInit(init: LibInit) {

    }
}