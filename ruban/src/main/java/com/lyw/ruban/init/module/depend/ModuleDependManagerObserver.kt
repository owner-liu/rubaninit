package com.lyw.ruban.init.module.depend

import com.lyw.ruban.ICompleteListener
import com.lyw.ruban.IInitCompleteObserverOperate
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.DependManagerObserver
import java.lang.ref.SoftReference

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

    var mInitCompleteObserver = hashMapOf<String, ArrayList<SoftReference<ICompleteListener>>>()

    override fun onCompleted(context: InitContext, aliasName: String) {
        context.logger.i(msg = "complete-module:$moduleAliasName,init:$aliasName")
        mInitCompletedAliases.add(aliasName)

        //监听整个module完成～
        if (initCount == mInitCompletedAliases.size) {
            context.logger.i(msg = "complete-module:$moduleAliasName")
            mObserver?.onCompleted(context, moduleAliasName)
        }

        //监听对应的init 完成～
        mInitCompleteObserver.get(aliasName)?.forEach {
            it.get()?.onCompleted()
        }

        val waitToInitList = mWaitToInitMap.remove(aliasName)
        waitToInitList?.forEach {
            it.value.forEach {
                it.refreshDependComplete(aliasName)
                it.initialize(context, this@ModuleDependManagerObserver)
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