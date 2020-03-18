package com.lyw.ruban

import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.lib.LibInit

/**
 * Created on  2020-03-17
 * Created by  lyw
 * Created for interface ~
 */
interface IAppOperate {
    fun addLibInit(libInit: LibInit)
}

interface IAppDependOperate : IAppOperate {
    fun addModuleDependAlias(moduleCode: Int, list: ArrayList<String>)
}

interface IAppLazyOperate : IAppOperate,
    IAppDependOperate {

    fun setModuleLazy(moduleCode: Int)

    fun initializeLazy(context: InitContext, moduleCodes: ArrayList<Int>)

    fun initializeLazyAll(context: InitContext)
}

interface ICompleteListener {
    fun onCompleted()
}

interface IModuleCompleteObserverOperate {
    fun addModuleCompletedListener(moduleAliases: HashSet<Int>, listener: ICompleteListener)
}

interface IAppCompleteObserverOperate {
    fun addAppCompletedListener(listener: ICompleteListener)
}

