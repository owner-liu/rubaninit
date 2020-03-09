package com.lyw.ruban.init.widgets

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
    threadCode: Int,
    var ThreadLibInit: LibInit
) : ThreadInitContainer<IInitObserver>(threadCode, ThreadLibInit) {

    override fun getAliasName(): String {
        return ThreadLibInit.getAliasName()
    }
}