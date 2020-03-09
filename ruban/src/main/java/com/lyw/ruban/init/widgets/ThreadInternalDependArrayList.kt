package com.lyw.ruban.init.widgets

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.thread.ThreadInitContainer

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