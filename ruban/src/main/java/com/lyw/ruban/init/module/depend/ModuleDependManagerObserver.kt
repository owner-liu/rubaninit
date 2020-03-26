package com.lyw.ruban.init.module.depend

import android.util.Log
import com.lyw.ruban.ICompleteListener
import com.lyw.ruban.IInitCompleteObserverOperate
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.AbsDependInit
import com.lyw.ruban.core.depend.BaseDependObserverProxy
import com.lyw.ruban.core.depend.DependManagerObserver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for depend manager observer~
 */
class ModuleDependManagerObserver
constructor(var moduleAliasName: String) :
    DependManagerObserver(),
    IDependInitObserver,
    IInitCompleteObserverOperate {

    var mInitCompleteObserver = hashMapOf<String, ArrayList<ICompleteListener>>()

    override fun onCompleted(context: InitContext, aliasName: String) {
        Log.i("ruban", "complete-module:$moduleAliasName,init:$aliasName")
        mInitCompletedAliases.add(aliasName)

        //监听整个module完成～
        if (initCount == mInitCompletedAliases.size) {
            // LABEL BY LYW: 全部完成，外抛状态～
            // TODO by LYW: 2020-03-19 设置当前的hasComplete～
            Log.i("ruban", "complete-module:$moduleAliasName")
            mObserver?.onCompleted(context, moduleAliasName)
        }

        //监听对应的init 完成～
        mInitCompleteObserver.get(aliasName)?.forEach {
            it.onCompleted()
        }

        val waitToInitList = mWaitToInitMap.remove(aliasName)
        waitToInitList?.forEach {
            context.mInitScope.launch {
                withContext(Dispatchers.Main) {
                    it.refreshDependComplete(aliasName)
                    it.initialize(context, this@ModuleDependManagerObserver)
                }
            }
        }
    }

    override fun addInitCompletedListener(
        moduleCode: Int,
        InitAliasName: String,
        listener: ICompleteListener
    ) {
        if (mInitCompletedAliases.contains(InitAliasName)) {
            listener.onCompleted()
        } else {
            var list = mInitCompleteObserver.get(InitAliasName) ?: let {
                arrayListOf<ICompleteListener>().also {
                    mInitCompleteObserver[InitAliasName] = it
                }
            }
            list.add(listener)
        }
    }
}