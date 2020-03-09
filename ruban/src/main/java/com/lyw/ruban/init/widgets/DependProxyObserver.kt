package com.lyw.ruban.init.widgets

import com.lyw.ruban.core.BaseObserverProxy
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.AbsDependInit

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for depend proxy observer~
 */
class DependProxyObserver<T : IInitObserver>
    : BaseObserverProxy<IDependInitObserver<T>>(),
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