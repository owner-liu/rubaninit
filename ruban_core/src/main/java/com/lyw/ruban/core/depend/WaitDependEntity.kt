package com.lyw.ruban.core.depend

import com.lyw.ruban.core.ConstantsForCore
import com.lyw.ruban.core.IDependInitObserver

/**
 * Created on  2020-03-26
 * Created by  lyw
 * Created for
 */
class WaitDependEntity {
    var threadCode: Int = ConstantsForCore.THREAD_SYNC

    var init: AbsDependInit<IDependInitObserver>? = null
}