package com.lyw.ruban.init.widgets.lazy

import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.lazy.LazyInitContainer
import com.lyw.ruban.init.widgets.depend.DependModule

/**
 * Created on  2020-03-17
 * Created by  lyw
 * Created for lazy depend module～
 */
open class LazyDependModule
constructor(
    moduleCode: Int,
    lazy: Boolean = false,
    aliasList: ArrayList<String> = arrayListOf()
) : LazyInitContainer<IDependInitObserver>(
    lazy,
    DependModule(moduleCode, aliasList)
)