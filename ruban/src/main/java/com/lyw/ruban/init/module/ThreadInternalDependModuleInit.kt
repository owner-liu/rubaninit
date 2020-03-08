package com.lyw.ruban.init.module

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IInitMap
import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.depend.DependInitContainer
import com.lyw.ruban.init.widgets.CommThreadArrayList

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for module 内，线程列表间无依赖～ 库之间存在依赖，并且依赖是线程内部依赖～
 */
abstract class ThreadInternalDependModuleInit
constructor(
    var moduleCode: Int
) : IInitMap<Int, CommThreadArrayList, IInitObserver>,
    AbsBaseInit<IInitObserver>(),
    IModule<CommThreadArrayList, DependInitContainer<IInitObserver>> {
}
