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
    : BaseObserverProxy<T>(),
    IDependInitObserver<T> {

    private var mWaitToInitMap = hashMapOf<String, ArrayList<AbsDependInit<T>>>()

    override fun onCompleted(context: InitContext, aliasName: String) {
        Log.i("ruban_test", "aliasName:$aliasName")
        mInitCompletedAliases.add(aliasName)
        // TODO by LYW: 2020-03-08 外抛～
        mObserver?.onCompleted(context, aliasName)
        // TODO by LYW: 2020-03-08 筛选出 依赖与 aliasName 的 ，进行处理～
        val waitToInits = mWaitToInitMap.remove(aliasName)
        waitToInits?.forEach {
            it.refreshDependComplete(aliasName)
            it.initialize(context, this as T)
        }
    }

    override fun onWaitToInit(
        context: InitContext,
        init: AbsDependInit<T>,
        dependAliasName: String
    ) {
        Log.i("ruban_test", "waitToInit:${init.getAliasName()}-depend:$dependAliasName")
        var list = mWaitToInitMap.get(dependAliasName) ?: let {
            arrayListOf<AbsDependInit<T>>().also {
                mWaitToInitMap[dependAliasName] = it
            }
        }
        list.add(init)
    }
}