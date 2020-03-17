package com.lyw.ruban.core.comm

import android.util.Log
import androidx.annotation.CallSuper
import com.lyw.ruban.core.BaseDependObserverProxy
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.AbsDependInit

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for depend manager observer~
 */
open class DependManagerObserver
    : BaseDependObserverProxy(),
    IDependInitObserver {

    @CallSuper
    override fun onCompleted(context: InitContext, aliasName: String) {
        mInitCompletedAliases.add(aliasName)
        mObserver?.onCompleted(context, aliasName)
        val waitToInitList = mWaitToInitMap.remove(aliasName)
        waitToInitList?.forEach {
            context.syncHandle.postAtFrontOfQueue {
                it.refreshDependComplete(aliasName)
                it.initialize(context, this)
            }
        }
    }

    @CallSuper
    override fun onWaitToInit(
        context: InitContext,
        init: AbsDependInit<IDependInitObserver>,
        dependAliasName: String
    ) {
        Log.i("ruban_test", "dependAliasName:$dependAliasName")
        if (mInitCompletedAliases.contains(dependAliasName)) {
            init.refreshDependComplete(dependAliasName)
            init.initialize(context, this)
        } else {
            var list = mWaitToInitMap.get(dependAliasName) ?: let {
                arrayListOf<AbsDependInit<IDependInitObserver>>().also {
                    mWaitToInitMap[dependAliasName] = it
                }
            }
            list.add(init)
        }
    }
}