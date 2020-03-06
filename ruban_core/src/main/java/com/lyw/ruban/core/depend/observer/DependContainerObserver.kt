package com.lyw.ruban.core.depend.observer

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.DependInitContainer
import com.lyw.ruban.core.depend.IDependInitObserver

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for depend observer~
 */
class DependContainerObserver<T : AbsBaseInit<IInitObserver>>
constructor(
    private var container: DependInitContainer<T>?,
    private var init: T
) : IInitObserver {

    var mObserver: IDependInitObserver? = null

    override fun onCompleted(context: InitContext, aliasName: String) {
        container?.hasInit = true
        init.hasInit = true
        mObserver?.onCompleted(context, init.getAliasName())
    }
}