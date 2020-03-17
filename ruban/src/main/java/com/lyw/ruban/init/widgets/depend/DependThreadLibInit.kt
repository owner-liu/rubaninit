package com.lyw.ruban.init.widgets.depend

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.depend.DependInitContainer
import com.lyw.ruban.init.lib.LibInit
import com.lyw.ruban.init.widgets.thread.ThreadLibInit

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