package com.lyw.ruban.init.app

import com.lyw.ruban.*
import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitMap
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.lib.LibInit
import com.lyw.ruban.init.module.ModuleConfig
import com.lyw.ruban.init.module.depend.ModuleLibExternalDependMap
import com.lyw.ruban.init.widgets.depend.DependModule
import com.lyw.ruban.init.widgets.depend.DependThreadLibInit
import com.lyw.ruban.init.widgets.lazy.LazyDependModule
import java.lang.IllegalArgumentException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashSet

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for app module 存在依赖，初始化库存在module内的依赖～
 */

class LazyAppDependInit
    : IInitMap<Int, LazyDependModule, IDependInitObserver>,
    AbsBaseInit<IDependInitObserver>(),
    IAppOperate,
    IModuleConfig,
    IAppLazyOperate,
    IInitCompleteObserverOperate,
    IModuleCompleteObserverOperate,
    IAppCompleteObserverOperate {

    private var mManagerObserver = AppManagerObserver()
    private var hasStartInit = false

    override var mData: Map<Int, LazyDependModule> = TreeMap()

    override fun put(key: Int, value: LazyDependModule) {
        (mData as TreeMap).put(key, value)
    }

    fun initialize(context: InitContext) {
        hasStartInit = true
        super.initialize(context, mManagerObserver)
    }

    override fun initialize(context: InitContext, observer: IDependInitObserver) {
        throw IllegalArgumentException("err, please invoke initialize(context: InitContext)")
    }

    override fun doInit(
        context: InitContext,
        key: Int,
        value: LazyDependModule?,
        observer: IDependInitObserver
    ) {
        value?.initialize(context, observer)
    }

    override fun getAliasName(): String {
        return javaClass.simpleName
    }

    override fun addLibInit(libInit: LibInit) {
        val moduleCode = libInit.libModuleCode
        val dependContainer =
            DependThreadLibInit(libInit)
        addDependLibInit(moduleCode, dependContainer)
    }

    private fun addDependLibInit(moduleCode: Int, init: DependThreadLibInit) {
        val module = getModule(moduleCode)
        ((module.init as DependModule).init as ModuleLibExternalDependMap).addInit(init)
    }

    override fun addModuleCompletedListener(
        moduleAliases: HashSet<Int>,
        listener: ICompleteListener
    ) {
        mManagerObserver.addModuleCompletedListener(moduleAliases, listener)
    }

    override fun addAppInitiativeCompletedListener(listener: ICompleteListener) {
        mManagerObserver.addAppInitiativeCompletedListener(listener)
    }

    override fun configModule(config: ModuleConfig) {
        val moduleCode = config.moduleCode
        val module = getModule(moduleCode)
        if (config.lazy) {
            mManagerObserver.configLazy(config.moduleCode.toString())
            module.setLazy(true)
        }
        val aliasList = config.dependAlias ?: return
        (module.init as DependModule).addAliasList(aliasList)
    }

    override fun initializeLazy(context: InitContext, moduleCodes: ArrayList<Int>) {
        moduleCodes.forEach {
            get(it)?.initializeLazy(context, mManagerObserver)
        }
    }

    override fun initializeLazyAll(context: InitContext) {
        getData().filter { it.value.checkLazy() }.forEach {
            it.value.initializeLazy(context, mManagerObserver)
        }
    }

    override fun addInitCompletedListener(
        moduleCode: Int,
        InitAliasName: String,
        listener: ICompleteListener
    ) {
        val module = getModule(moduleCode)
        ((module.init as DependModule).init as ModuleLibExternalDependMap).addInitCompletedListener(
            moduleCode, InitAliasName, listener
        )
    }

    /**
     * 添加module~
     */
    private fun getModule(moduleCode: Int): LazyDependModule {
        return get(moduleCode) ?: LazyDependModule(moduleCode).also {
            if (hasStartInit) {
                //开始初始化后，添加的module统一为lazy~
                it.setLazy(true)
            }
            mManagerObserver.addModule(moduleCode)
            put(moduleCode, it)
        }
    }
}