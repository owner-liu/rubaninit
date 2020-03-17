package com.lyw.ruban.init.app

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitMap
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.lib.LibInit
import com.lyw.ruban.init.module.depend.ThreadListExternalDependModuleInit
import com.lyw.ruban.init.widgets.depend.DependModule
import com.lyw.ruban.init.widgets.depend.DependThreadLibInit
import com.lyw.ruban.init.widgets.lazy.LazyDependModule
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
    IAppDependOperate,
    IModuleCompleteObserverOperate,
    IAppCompleteObserverOperate,
    IAppLazyOperate {

    private var mManagerObserver = AppManagerObserver()

    override var mData: Map<Int, LazyDependModule> = TreeMap()

    override fun put(key: Int, value: LazyDependModule) {
        (mData as TreeMap).put(key, value)
    }

    override fun initialize(context: InitContext, observer: IDependInitObserver) {
        mManagerObserver.mModuleCount = mData.size
        super.initialize(context, observer)
    }

    override fun doInit(
        context: InitContext,
        key: Int,
        value: LazyDependModule?,
        observer: IDependInitObserver
    ) {
        mManagerObserver.mObserver = observer
        value?.initialize(context, mManagerObserver)
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
        val module = get(moduleCode) ?: let {
            LazyDependModule(moduleCode).also {
                put(moduleCode, it)
            }
        }

        (module.init as ThreadListExternalDependModuleInit).addInit(init)
    }

    override fun addModuleDependAlias(moduleCode: Int, list: ArrayList<String>) {
        val module = get(moduleCode) ?: let {
            LazyDependModule(moduleCode).also {
                put(moduleCode, it)
            }
        }
        (module.init as DependModule).addAliasList(list)
    }

    override fun addModuleCompletedListener(
        moduleAliases: HashSet<Int>,
        listener: ICompleteListener
    ) {
        mManagerObserver.addModuleCompletedListener(moduleAliases, listener)
    }

    override fun addAppCompletedListener(listener: ICompleteListener) {
        mManagerObserver.addAppCompletedListener(listener)
    }

    override fun initializeLazy(context: InitContext, moduleCode: Int) {
        // TODO by LYW: 2020-03-17 针对于 延迟初始化的，存在依赖的～ 需要甄别，后续会不会再执行，比如，depend触发的再执行，未轮训到的再执行～ 需要考虑是否需要锁～
        get(moduleCode)?.initializeLazy(context, mManagerObserver)
    }
}