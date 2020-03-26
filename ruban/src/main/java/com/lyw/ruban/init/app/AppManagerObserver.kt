package com.lyw.ruban.init.app

import com.lyw.ruban.IAppCompleteObserverOperate
import com.lyw.ruban.ICompleteListener
import com.lyw.ruban.IModuleCompleteListener
import com.lyw.ruban.IModuleCompleteObserverOperate
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.DependManagerObserver
import java.lang.IllegalArgumentException

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
    private val mAppObserverList = arrayListOf<ICompleteListener>()

    //module 数量～
    var mModuleCount: Int = 0
    //是否完成全部初始化～
    private var mAppInitComplete = false

    override fun addModuleCompletedListener(
        moduleAliases: HashSet<Int>,
        listener: ICompleteListener
    ) {
        val moduleCompeteObserver = ModuleCompeteObserver(moduleAliases, listener)
        mInitCompletedAliases.forEach { moduleCompeteObserver.onCompleted(it) }
        mObserverList.add(moduleCompeteObserver)
    }

    override fun addAppCompletedListener(listener: ICompleteListener) {
        if (mAppInitComplete) {
            listener.onCompleted()
            return
        }
        mAppObserverList.add(listener)
    }

    override fun onCompleted(context: InitContext, aliasName: String) {
        mObserverList.forEach { it.onCompleted(aliasName) }

        super.onCompleted(context, aliasName)

        if (mModuleCount == mInitCompletedAliases.size) {
            mAppInitComplete = true
            mAppObserverList.forEach { it.onCompleted() }
        }
    }
}

class ModuleCompeteObserver
constructor(
    private var moduleAliases: HashSet<Int>,
    private var listener: ICompleteListener
) : IModuleCompleteListener {

    init {
        if (moduleAliases.isEmpty()) {
            throw IllegalArgumentException("添加了无效的module完成监听～")
        }
    }

    override fun onCompleted(aliasName: String) {
        if (moduleAliases.isEmpty()) {
            return
        }

        aliasName.toIntOrNull()?.let {
            moduleAliases.remove(it)
            if (moduleAliases.isEmpty()) {
                listener.onCompleted()
            }
        }
    }
}