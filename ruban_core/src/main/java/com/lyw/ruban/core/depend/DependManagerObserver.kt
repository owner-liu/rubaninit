package com.lyw.ruban.core.depend

import android.util.Log
import com.lyw.ruban.core.ConstantsForCore
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.thread.ThreadInitContainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for depend manager observer~
 */
open class DependManagerObserver
    : BaseDependObserverProxy(),
    IDependInitObserver {

    override fun onCompleted(context: InitContext, aliasName: String) {
        mInitCompletedAliases.add(aliasName)
        mObserver?.onCompleted(context, aliasName)
        val waitToInitList = mWaitToInitMap.remove(aliasName)

        waitToInitList?.forEach {
            it.value.forEach {
                it.refreshDependComplete(aliasName)
                it.initialize(context, this@DependManagerObserver)
            }
        }
    }

    override fun onWaitToInit(
        context: InitContext,
        init: AbsDependInit<IDependInitObserver>,
        dependAliasName: String
    ) {
        Log.i("ruban", "wait-dependAliasName:$dependAliasName")

        val threadCode =
            ((init as? DependInitContainer<*>)?.init as? ThreadInitContainer)?.getCurrentThreadCode()
                ?: ConstantsForCore.THREAD_SYNC

        if (mInitCompletedAliases.contains(dependAliasName)) {
            //直接执行～
            init.refreshDependComplete(dependAliasName)
            init.initialize(context, this)
        } else {
            val treeMap = mWaitToInitMap.get(dependAliasName) ?: let {
                TreeMap<Int, ArrayList<AbsDependInit<IDependInitObserver>>>().also {
                    mWaitToInitMap.put(dependAliasName, it)
                }
            }
            val list = treeMap.get(threadCode) ?: let {
                arrayListOf<AbsDependInit<IDependInitObserver>>().also {
                    treeMap.put(threadCode, it)
                }
            }
            list.add(init)
        }
    }
}