package com.lyw.ruban.init.module.depend

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.depend.AbsDependInit
import com.lyw.ruban.core.thread.AbsThreadInit
import com.lyw.ruban.init.module.thread.AbsThreadModuleInit

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for abs module init~
 */
abstract class AbsThreadDependModuleInit<T : AbsThreadInit<Z>,
        U : AbsDependInit<Z>,
        Z : IInitObserver>
    : AbsThreadModuleInit<T, U, Z>() {

    //是否是懒加载～
    var lazy: Boolean = false
}