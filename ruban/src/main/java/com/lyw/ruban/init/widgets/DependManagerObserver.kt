package com.lyw.ruban.init.widgets

import android.util.Log
import com.lyw.ruban.core.BaseDependObserverProxy
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.AbsDependInit

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for depend manager observer~
 */
class DependManagerObserver<T : IInitObserver>
    : BaseDependObserverProxy<T>(),
    IDependInitObserver<T> {

    override fun onCompleted(context: InitContext, aliasName: String) {
        synchronized(lock)
        {
            mInitCompletedAliases.add(aliasName)
            mObserver?.onCompleted(context, aliasName)
            val waitToInitList = mWaitToInitMap.remove(aliasName)
            waitToInitList?.forEach {
                it.refreshDependComplete(aliasName)
                it.initialize(context, this as T)
            }
        }
    }

    override fun onWaitToInit(
        context: InitContext,
        init: AbsDependInit<T>,
        dependAliasName: String
    ) {
        synchronized(lock)
        {
            if (mInitCompletedAliases.contains(dependAliasName)) {
                init.refreshDependComplete(dependAliasName)
                init.initialize(context, this as T)
            } else {
                var list = mWaitToInitMap.get(dependAliasName) ?: let {
                    arrayListOf<AbsDependInit<T>>().also {
                        mWaitToInitMap[dependAliasName] = it
                    }
                }
                list.add(init)
            }
        }
    }
}