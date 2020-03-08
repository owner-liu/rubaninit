package com.lyw.ruban.init.widgets

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.depend.DependInitContainer
import com.lyw.ruban.core.thread.ThreadInitContainer
import com.lyw.ruban.init.lib.LibInit

/**
 * Created on  2020-03-07
 * Created by  lyw
 * Created for
 */
class ThreadExternalDependArrayList
constructor(
    threadCode: Int,
    var commThreadArrayList: LibExternalDependInitArrayList = LibExternalDependInitArrayList()
) : ThreadInitContainer<IInitObserver>(threadCode, commThreadArrayList)
