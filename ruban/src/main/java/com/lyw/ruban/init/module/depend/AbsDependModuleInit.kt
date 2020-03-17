package com.lyw.ruban.init.module.depend

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.depend.AbsDependInit
import com.lyw.ruban.core.thread.AbsThreadInit
import com.lyw.ruban.init.module.IModule
import com.lyw.ruban.init.module.comm.AbsModuleInit

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for abs module init~
 */
abstract class AbsDependModuleInit<T : AbsThreadInit<Z>,
        U : AbsDependInit<Z>,
        Z : IInitObserver>
    : AbsModuleInit<T, U, Z>() {

    //是否是懒加载～
    var lazy: Boolean = false
}