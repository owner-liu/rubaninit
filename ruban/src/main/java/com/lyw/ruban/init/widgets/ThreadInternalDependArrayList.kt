package com.lyw.ruban.init.widgets

import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInit
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.InitContext
import com.lyw.ruban.core.thread.AbsThreadList
import com.lyw.ruban.core.thread.ThreadInitContainer
import com.lyw.ruban.init.lib.LibInit

/**
 * Created on  2020-03-07
 * Created by  lyw
 * Created for
 */
class ThreadInternalDependArrayList
constructor(
    threadCode: Int,
    var commThreadArrayList: LibInternalDependInitArrayList = LibInternalDependInitArrayList()
) : ThreadInitContainer<IInitObserver>(threadCode, commThreadArrayList)