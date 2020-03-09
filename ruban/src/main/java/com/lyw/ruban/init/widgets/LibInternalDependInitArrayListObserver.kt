package com.lyw.ruban.init.widgets

import android.util.Log
import com.lyw.ruban.core.*
import com.lyw.ruban.core.depend.AbsDependInit

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for  LibInternalDependInitArrayList observer～
 */
class LibInternalDependInitArrayListObserver<T : IInitObserver>
    : BaseDependObserverProxy<T>(),
    IDependInitObserver<T> {

    override fun onCompleted(context: InitContext, aliasName: String) {
        Log.i("ruban_test", "aliasName:$aliasName")
        mInitCompletedAliases.add(aliasName)
        mObserver?.onCompleted(context, aliasName)
        val waitToInitList = mWaitToInitMap.remove(aliasName)
        waitToInitList?.forEach {
            it.refreshDependComplete(aliasName)
            it.initialize(context, this as T)
        }
    }

    override fun onWaitToInit(
        context: InitContext,
        init: AbsDependInit<T>,
        dependAliasName: String
    ) {
        var list = mWaitToInitMap.get(dependAliasName) ?: let {
            arrayListOf<AbsDependInit<T>>().also {
                mWaitToInitMap[dependAliasName] = it
            }
        }
        list.add(init)
    }
}