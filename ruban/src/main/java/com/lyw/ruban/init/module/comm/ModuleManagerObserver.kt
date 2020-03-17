package com.lyw.ruban.init.module.comm

import android.util.Log
import com.lyw.ruban.core.*
import com.lyw.ruban.core.depend.AbsDependInit

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for depend manager observer~
 */
class ModuleManagerObserver
constructor(var moduleAliasName: String) : BaseObserverProxy<IInitObserver>(),
    IInitObserver {

    override fun onCompleted(context: InitContext, aliasName: String) {
        mInitCompletedAliases.add(aliasName)
        if (initCount == mInitCompletedAliases.size) {
            // LABEL BY LYW: 全部完成，外抛状态～
            mObserver?.onCompleted(context, moduleAliasName)
        }
    }
}