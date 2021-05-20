package com.lyw.ruban

import com.lyw.ruban.core.ILogger
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

interface IAppLazyOperate : IAppOperate {

    fun initializeLazy(context: InitContext, moduleCodes: HashSet<Int>)

    fun initializeLazyAll(context: InitContext)

    fun hasStartInit(): Boolean
}

interface IAppOperateManager : IAppOperate {

    fun initializeLazy(moduleCode: Int)

    fun initializeLazy(moduleCodes: HashSet<Int>)

    fun initializeLazyAll()

    fun hasStartInit(): Boolean
}

interface IModuleConfig {
    fun configModule(config: ModuleConfig)
}

interface ICompleteListener {
    fun onCompleted()
}

interface IInitCompleteObserverOperate {
    fun addInitCompletedListener(
        moduleCode: Int,
        InitAliasName: String,
        listener: ICompleteListener
    )
}

interface IModuleCompleteListener {
    fun onCompleted(aliasName: String)
}

interface IModuleCompleteObserverOperate {
    fun addModuleCompletedListener(moduleAliase: Int, listener: ICompleteListener)

    fun addModuleCompletedListener(moduleAliases: HashSet<Int>, listener: ICompleteListener)
}

interface IAppCompleteObserverOperate {
    fun addAppInitiativeCompletedListener(listener: ICompleteListener)
}


interface IModule<T, U> {

    fun addInit(init: U)
}

interface ILoggerManager {
    fun setLogger(iLogger: ILogger?)
}