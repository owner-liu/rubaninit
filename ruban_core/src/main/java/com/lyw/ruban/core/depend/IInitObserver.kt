package com.lyw.ruban.core.depend

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for depend observer
 */
interface IDependInitObserver : IInitObserver {

    fun waitToInit(context: InitContext, init: AbsBaseDependInit, dependAliasName: String)
}

interface IDependGroupObserver : IInitObserver {

    fun waitToInit(context: InitContext, init: AbsDependInitGroup, dependAliasName: String)
}
