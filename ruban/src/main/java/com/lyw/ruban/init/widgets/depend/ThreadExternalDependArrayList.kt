package com.lyw.ruban.init.widgets.depend

import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.thread.ThreadInitContainer
import com.lyw.ruban.init.widgets.depend.LibExternalDependInitArrayList

/**
 * Created on  2020-03-07
 * Created by  lyw
 * Created for 集合内的lib初始化，依赖与其他集合内的 lib 初始化～
 */
class ThreadExternalDependArrayList
constructor(
    threadCode: Int,
    var commThreadArrayList: LibExternalDependInitArrayList = LibExternalDependInitArrayList()
) : ThreadInitContainer<IDependInitObserver>(threadCode, commThreadArrayList)
