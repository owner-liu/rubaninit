package com.lyw.ruban.core.depend

import com.lyw.ruban.core.*

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for depend proxy observer~
 */
class DependProxyObserver<T : IInitObserver>
constructor(var init: AbsInit) : BaseObserverProxy<IDependInitObserver>(),
    IDependInitObserver {

    override fun onCompleted(context: InitContext, aliasName: String) {
        init.hasInitComplete = true
        mObserver?.onCompleted(context, aliasName)
    }

    override fun onWaitToInit(
        context: InitContext,
        init: AbsDependInit<IDependInitObserver>,
        dependAliasName: String
    ) {
        mObserver?.onWaitToInit(context, init, dependAliasName)
    }
}