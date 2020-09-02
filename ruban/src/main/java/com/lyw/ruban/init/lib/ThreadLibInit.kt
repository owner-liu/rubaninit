package com.lyw.ruban.init.lib

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.thread.ThreadInitContainer
import com.lyw.ruban.init.lib.LibInit

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for thread lib init
 */
class ThreadLibInit
constructor(
    libInit: LibInit
) : ThreadInitContainer<IInitObserver>(
    libInit.libThreadCode,
    libInit
)