package com.lyw.ruban.init.app

import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.comm.DependManagerObserver
import java.lang.IllegalArgumentException

/**
 * Created on  2020-03-14
 * Created by  lyw
 * Created for module init complete manager~
 */
class AppManagerObserver<T : IInitObserver>
    : DependManagerObserver<T>(),
    IModuleCompleteObserverOperate,
    IAppCompleteObserverOperate,
    IDependInitObserver {
    // module 监测～
    private val mObserverList = arrayListOf<ModuleCompeteObserver>()
    private val mAppObserverList = arrayListOf<ICompleteListener>()

    private var mContext: InitContext? = null

    //module 数量～
    var mModuleCount: Int = 0
    //是否完成全部初始化～
    private var mAppInitComplete = false

    override fun addModuleCompletedListener(
        moduleAliases: HashSet<Int>,
        listener: ICompleteListener
    ) {
        val moduleCompeteObserver = ModuleCompeteObserver(moduleAliases, listener)
        mContext?.let { context ->
            mInitCompletedAliases.forEach { moduleCompeteObserver.onCompleted(context, it) }
        }
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
        mContext = context
        mObserverList.forEach { it.onCompleted(context, aliasName) }

        super.onCompleted(context, aliasName)


        // TODO by LYW: 2020-03-16 判断逻辑需要优化～ 因为内部会继续去初始化相关的数据～

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
) : IInitObserver {

    init {
        if (moduleAliases.isEmpty()) {
            throw IllegalArgumentException("添加了无效的module完成监听～")
        }
    }

    override fun onCompleted(context: InitContext, aliasName: String) {
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

interface IModuleCompleteObserverOperate {
    fun addModuleCompletedListener(moduleAliases: HashSet<Int>, listener: ICompleteListener)
}

interface IAppCompleteObserverOperate {
    fun addAppCompletedListener(listener: ICompleteListener)
}


interface ICompleteListener {
    fun onCompleted()
}