package com.lyw.ruban.init.app

import com.lyw.ruban.IAppCompleteObserverOperate
import com.lyw.ruban.ICompleteListener
import com.lyw.ruban.IModuleCompleteListener
import com.lyw.ruban.IModuleCompleteObserverOperate
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.DependManagerObserver
import java.lang.IllegalArgumentException
import java.lang.ref.SoftReference

/**
 * Created on  2020-03-14
 * Created by  lyw
 * Created for module init complete manager~
 */
class AppManagerObserver
    : DependManagerObserver(),
    IModuleCompleteObserverOperate,
    IAppCompleteObserverOperate,
    IDependInitObserver {
    // module 监测～
    private val mObserverList = arrayListOf<ModuleCompeteObserver>()
    private val mAppObserverList = arrayListOf<SoftReference<ICompleteListener>>()

    //主动初始化init 完成alias~
    private val mInitiativeModuleCompletedAliases = arrayListOf<String>()
    private val mLazyInitAliases = arrayListOf<String>()

    //module 数量～
    private var mInitiativeModuleCount: Int = 0

    //是否完成全部初始化～
    private var mAppInitComplete = false

    override fun addModuleCompletedListener(moduleAliase: Int, listener: ICompleteListener) {
        addModuleCompletedListener(hashSetOf(moduleAliase),listener)
    }

    override fun addModuleCompletedListener(
        moduleAliases: HashSet<Int>,
        listener: ICompleteListener
    ) {
        val moduleCompeteObserver = ModuleCompeteObserver(moduleAliases, listener)
        mInitCompletedAliases.forEach { moduleCompeteObserver.onCompleted(it) }
        mObserverList.add(moduleCompeteObserver)
    }

    override fun addAppInitiativeCompletedListener(listener: ICompleteListener) {
        if (mAppInitComplete) {
            listener.onCompleted()
            return
        }
        mAppObserverList.add(SoftReference(listener))
    }

    override fun onCompleted(context: InitContext, aliasName: String) {
        // 优先触发相关监听回调~
        mObserverList.forEach { it.onCompleted(aliasName) }

        // 触发相关依赖的库的初始化～
        super.onCompleted(context, aliasName)

        // 判断是否已经初始化完成～
        if (!mLazyInitAliases.contains(aliasName)) {
            //非延迟初始化库初始化完成时触发校验检测~
            mInitiativeModuleCompletedAliases.add(aliasName)
            if (mInitiativeModuleCount == mInitiativeModuleCompletedAliases.size) {
                mAppInitComplete = true
                mAppObserverList.forEach { it.get()?.onCompleted() }
            }
        }
    }

    /**
     * 新增module~
     */
    fun addModule(moduleCode: Int) {
        mInitiativeModuleCount++
    }

    /**
     * 配置 lazy~
     */
    fun configLazy(aliasName: String) {
        if (!mLazyInitAliases.contains(aliasName)) {
            mInitiativeModuleCount--
            mLazyInitAliases.add(aliasName)
        }
    }
}

class ModuleCompeteObserver
constructor(
    private var moduleAliases: HashSet<Int>,
    listener: ICompleteListener
) : IModuleCompleteListener {

    var mListener: SoftReference<ICompleteListener>

    init {
        if (moduleAliases.isEmpty()) {
            throw IllegalArgumentException("添加了无效的module完成监听～")
        }
        mListener = SoftReference(listener)
    }

    override fun onCompleted(aliasName: String) {
        if (moduleAliases.isEmpty()) {
            return
        }

        aliasName.toIntOrNull()?.let {
            moduleAliases.remove(it)
            if (moduleAliases.isEmpty()) {
                mListener.get()?.onCompleted()
            }
        }
    }
}