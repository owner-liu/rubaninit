package com.lyw.ruban.core.depend

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for depend init observer
 */
interface IDependInitObserver : IInitObserver {

    fun waitToInit(context: InitContext, init: BaseDependInit, depend: String)
}