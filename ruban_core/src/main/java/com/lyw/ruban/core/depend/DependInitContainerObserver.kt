package com.lyw.ruban.core.depend

import com.lyw.ruban.core.*


/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for depend container observer~
 */
class DependInitContainerObserver<T : IInitObserver>
    : BaseObserverProxy<IDependInitObserver<T>>(),
    IDependInitObserver<T> {

    override fun onCompleted(context: InitContext, aliasName: String) {
        mObserver?.onCompleted(context, aliasName)
    }

    override fun onWaitToInit(context: InitContext, init: AbsDependInit<T>, dependAliasName: String) {
        mObserver?.onWaitToInit(context, init, dependAliasName)
    }
}