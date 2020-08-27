package com.lyw.ruban.init.widgets.depend.module

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.depend.AbsDependInit

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for abs module init~
 */
abstract class AbsModuleThreadInit<T : AbsBaseInit<Z>,
        U : AbsDependInit<Z>,
        Z : IInitObserver>
    : AbsModuleInit<T, U, Z>()