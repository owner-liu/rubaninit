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
    IAppManagerObserver,
    IDependInitObserver {
    private val mObserverList = arrayListOf<ModuleCompeteObserver>()

    private var mContext: InitContext? = null

    override fun addCompletedListener(
        moduleAliases: HashSet<Int>,
        listener: IModulesInitCompleteListener
    ) {
        synchronized(lock)
        {
            val moduleCompeteObserver =
                ModuleCompeteObserver(
                    moduleAliases,
                    listener
                )

            mContext?.let { context ->
                mInitCompletedAliases.forEach { moduleCompeteObserver.onCompleted(context, it) }
            }

            mObserverList.add(moduleCompeteObserver)
        }
    }

    override fun onCompleted(context: InitContext, aliasName: String) {
        synchronized(lock) {
            mContext = context
            super.onCompleted(context, aliasName)
            mObserverList.forEach { it.onCompleted(context, aliasName) }
        }
    }
}

class ModuleCompeteObserver
constructor(
    var moduleAliases: HashSet<Int>,
    var listener: IModulesInitCompleteListener
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

interface IAppManagerObserver {
    fun addCompletedListener(moduleAliases: HashSet<Int>, listener: IModulesInitCompleteListener)
}

interface IModulesInitCompleteListener {
    fun onCompleted()
}