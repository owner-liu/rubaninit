package com.lyw.ruban.init.module

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.depend.AbsDependInit
import com.lyw.ruban.core.thread.AbsThreadInit

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for abs module init~
 */
abstract class AbsDependModuleInit<T : AbsThreadInit<Z>,
        U : AbsDependInit<IDependInitObserver>,
        Z : IInitObserver> :
    AbsBaseInit<Z>(), IModule<T, U> {

    //统计 添加的lib 数量～
    var libCount: Int = 0

    //是否是懒加载～
    var lazy: Boolean = false
}