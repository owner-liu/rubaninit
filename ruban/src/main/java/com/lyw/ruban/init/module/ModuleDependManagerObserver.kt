package com.lyw.ruban.init.module

import android.util.Log
import com.lyw.ruban.core.*
import com.lyw.ruban.core.depend.AbsDependInit

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for depend manager observer~
 */
class ModuleDependManagerObserver<T : IInitObserver>
constructor(
    var moduleAliasName: String,
    var module: AbsInit
) : BaseDependObserverProxy<T>(),
    IDependInitObserver {

    override fun onCompleted(context: InitContext, aliasName: String) {
        mInitCompletedAliases.add(aliasName)
        if (initCount == mInitCompletedAliases.size) {
            // LABEL BY LYW: 全部完成，外抛状态～
            module.hasInitComplete = true
            mObserver?.onCompleted(context, moduleAliasName)
        }
        val waitToInitList = mWaitToInitMap.remove(aliasName)
        waitToInitList?.forEach {
            it.refreshDependComplete(aliasName)
            it.initialize(context, this)
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