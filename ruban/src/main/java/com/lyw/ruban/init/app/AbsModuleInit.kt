package com.lyw.ruban.init.app

import com.lyw.ruban.IModule
import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IInitObserver

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for abs module init~
 */
abstract class AbsModuleInit<T : AbsBaseInit<Z>,
        U : AbsBaseInit<Z>,
        Z : IInitObserver>
    : AbsBaseInit<Z>()
    , IModule<T, U> {

    var initCount: Int = 0
}