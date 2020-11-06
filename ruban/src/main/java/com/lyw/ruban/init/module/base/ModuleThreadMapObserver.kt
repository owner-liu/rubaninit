package com.lyw.ruban.init.module.base

import com.lyw.ruban.ICompleteListener
import com.lyw.ruban.IInitCompleteObserverOperate
import com.lyw.ruban.core.AbsInit
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.DependManagerObserver
import java.lang.ref.SoftReference

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for depend manager observer~
 */
class ModuleThreadMapObserver
constructor(var init: AbsInit) :
    DependManagerObserver(),
    IDependInitObserver,
    IInitCompleteObserverOperate {

    var mInitCompleteObserver = hashMapOf<String, ArrayList<SoftReference<ICompleteListener>>>()

    override fun onCompleted(context: InitContext, aliasName: String) {
        context.logger.i(msg = "complete-module:${init.getAliasName()},init:$aliasName")
        mInitCompletedAliases.add(aliasName)

        //监听整个module完成～
        if (initCount == mInitCompletedAliases.size) {
            // 设置 当前 module 已完成～
            init.hasInitComplete = true
            context.logger.i(msg = "complete-module:${init.getAliasName()}")
            mObserver?.onCompleted(context, init.getAliasName())
        }

        //监听对应的init 完成～
        mInitCompleteObserver.get(aliasName)?.forEach {
            it.get()?.onCompleted()
        }

        val waitToInitList = mWaitToInitMap.remove(aliasName)
        waitToInitList?.forEach {
            it.value.forEach {
                it.refreshDependComplete(aliasName)
                it.initialize(context, this@ModuleThreadMapObserver)
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
                arrayListOf<SoftReference<ICompleteListener>>().also {
                    mInitCompleteObserver[InitAliasName] = it
                }
            }
            list.add(SoftReference(listener))
        }
    }
}