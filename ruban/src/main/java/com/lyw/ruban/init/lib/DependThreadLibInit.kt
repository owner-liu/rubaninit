package com.lyw.ruban.init.lib

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.depend.DependInitContainer

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for depend lib init
 */
class DependThreadLibInit
constructor(
    libInit: LibInit
) : DependInitContainer<IInitObserver>(
    libInit.libDependAlias,
    ThreadLibInit(libInit)
)