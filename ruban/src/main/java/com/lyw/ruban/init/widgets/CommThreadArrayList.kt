package com.lyw.ruban.init.widgets

import com.lyw.ruban.core.IInit
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.thread.ThreadInitContainer

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for
 */
class CommThreadArrayList
constructor(
    threadCode: Int,
    init: IInit<IInitObserver>
) : ThreadInitContainer<IInitObserver>(threadCode, init) {
}