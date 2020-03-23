package com.lyw.ruban

import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.lib.LibInit
import com.lyw.ruban.init.module.ModuleConfig

/**
 * Created on  2020-03-17
 * Created by  lyw
 * Created for interface ~
 */
interface IAppOperate {
    fun addLibInit(libInit: LibInit)
}

interface IAppLazyOperate : IAppOperate{

    fun initializeLazy(context: InitContext, moduleCodes: ArrayList<Int>)

    fun initializeLazyAll(context: InitContext)
}

interface IModuleConfig{
    fun configModule(config:ModuleConfig)
}

interface ICompleteListener {
    fun onCompleted()
}

interface IModuleCompleteListener {
    fun onCompleted(aliasName: String)
}

interface IModuleCompleteObserverOperate {
    fun addModuleCompletedListener(moduleAliases: HashSet<Int>, listener: ICompleteListener)
}

interface IAppCompleteObserverOperate {
    fun addAppCompletedListener(listener: ICompleteListener)
}
