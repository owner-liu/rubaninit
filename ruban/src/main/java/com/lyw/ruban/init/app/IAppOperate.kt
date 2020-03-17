package com.lyw.ruban.init.app

import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.lib.LibInit

/**
 * Created on  2020-03-17
 * Created by  lyw
 * Created for
 */
interface IAppOperate {
    fun addLibInit(libInit: LibInit)
}

interface IAppDependOperate : IAppOperate {
    fun addModuleDependAlias(moduleCode: Int, list: ArrayList<String>)
}

interface IAppLazyOperate : IAppOperate, IAppDependOperate {
    fun initializeLazy(context: InitContext, moduleCode: Int)
}