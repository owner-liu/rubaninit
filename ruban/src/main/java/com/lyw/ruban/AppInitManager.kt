package com.lyw.ruban

import android.app.Application
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.init.app.LazyAppDependInit
import com.lyw.ruban.init.lib.LibInit
import com.lyw.ruban.init.module.ModuleConfig
import java.lang.IllegalArgumentException

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for app 管理～
 *
 * 说明：
 * 1。初始化
 *      1。module～
 *              1。库的初始化以module为界
 *              2。module 可以设置延迟加载～
 *              3。module 之间可以存在依赖关系～
 *      2。lib
 *              1。lib 可以设置指定线程执行～
 *              2。lib 之间可以存在 module内的依赖关系
 *
 * 2。监听
 *      1。可以设置单个或者多个module的初始化完成监听～
 *      2。可以设置所有的module初始化完成监听（包含延迟初始化的module）～
 *
 * 3。针对于延迟初始化～
 *      1。延迟初始化的库之间禁止存在依赖关系～
 *
 *
 */
object AppInitManager
    : IAppOperate,
    IAppCompleteObserverOperate,
    IModuleConfig {

    private var mContext: InitContext? = null

    private val mLazyAppDependInit by lazy { LazyAppDependInit() }

    fun initialize(application: Application, isDebug: Boolean) {
        val context = InitContext(application, isDebug)
        mContext = context
        mLazyAppDependInit.initialize(context)
    }

    fun initializeLazy(moduleCodes: ArrayList<Int>) {
        mContext?.let {
            mLazyAppDependInit.initializeLazy(it, moduleCodes)
        } ?: let {
            throw IllegalArgumentException("please invoke initialize(application: Application, isDebug: Boolean)～")
        }
    }

    fun initializeLazyAll() {
        mContext?.let {
            mLazyAppDependInit.initializeLazyAll(it)
        } ?: let {
            throw IllegalArgumentException("please invoke initialize(application: Application, isDebug: Boolean)～")
        }
    }

    override fun configModule(config: ModuleConfig) {
        mLazyAppDependInit.configModule(config)
    }

    override fun addLibInit(libInit: LibInit) {
        mLazyAppDependInit.addLibInit(libInit)
    }

    fun addModuleCompletedListener(
        moduleAliases: HashSet<Int>,
        listener: ICompleteListener
    ) {
        mLazyAppDependInit.addModuleCompletedListener(moduleAliases, listener)
    }

    override fun addAppCompletedListener(listener: ICompleteListener) {
        mLazyAppDependInit.addAppCompletedListener(listener)
    }
}