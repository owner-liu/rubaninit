package com.lyw.ruban.init.module.depend

import android.util.Log
import com.lyw.ruban.core.depend.BaseDependObserverProxy
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.AbsDependInit

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for depend manager observer~
 */
class ModuleDependManagerObserver
constructor(var moduleAliasName: String) : BaseDependObserverProxy(),
    IDependInitObserver {

    override fun onCompleted(context: InitContext, aliasName: String) {
        Log.i("ruban_test", "$moduleAliasName-onCompleted:$aliasName")
        mInitCompletedAliases.add(aliasName)
        Log.i("ruban_test", "$moduleAliasName-initCount:$initCount")
        Log.i("ruban_test", "$moduleAliasName-mInitCompletedAliases:${mInitCompletedAliases.size}")
        if (initCount == mInitCompletedAliases.size) {
            // LABEL BY LYW: 全部完成，外抛状态～
            // TODO by LYW: 2020-03-19 设置当前的hasComplete～
            mObserver?.onCompleted(context, moduleAliasName)
        }
        val waitToInitList = mWaitToInitMap.remove(aliasName)
        waitToInitList?.forEach {
            context.syncHandle.postAtFrontOfQueue {
                it.refreshDependComplete(aliasName)
                it.initialize(context, this)
            }
        }

    }

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