package com.lyw.ruban.init.module.thread

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.thread.AbsThreadInit
import com.lyw.ruban.init.module.IModule

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for abs module init~
 */
abstract class AbsThreadModuleInit<T : AbsThreadInit<Z>,
        U : AbsBaseInit<Z>,
        Z : IInitObserver>
    : AbsBaseInit<Z>()
    , IModule<T, U> {

    var initCount: Int = 0
}