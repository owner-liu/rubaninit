package com.lyw.ruban.init.widgets

import android.util.Log
import com.lyw.ruban.core.*
import com.lyw.ruban.core.depend.AbsDependInit

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for  LibInternalDependInitArrayList observer～
 * 相关状态， 直接外抛~
 */
class LibExternalDependInitArrayListObserver<T : IInitObserver>
    : BaseDependObserverProxy<IDependInitObserver<T>>(),
    IDependInitObserver<T> {

    override fun onCompleted(context: InitContext, aliasName: String) {
        mObserver?.onCompleted(context, aliasName)
    }

    override fun onWaitToInit(
        context: InitContext,
        init: AbsDependInit<T>,
        dependAliasName: String
    ) {
        mObserver?.onWaitToInit(context, init, dependAliasName)
    }
}