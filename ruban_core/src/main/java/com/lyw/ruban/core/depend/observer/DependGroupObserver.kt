package com.lyw.ruban.core.depend.observer

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.*

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for depend observer~
 */
class DependGroupObserver<T, U>
constructor(
    private var init: AbsDependMap<T, U>
) : IInitObserver {

    var mObserver: IDependInitObserver? = null

    override fun onCompleted(context: InitContext, aliasName: String) {
        init.hasInit = true
        mObserver?.onCompleted(context, init.getAliasName())
    }
}