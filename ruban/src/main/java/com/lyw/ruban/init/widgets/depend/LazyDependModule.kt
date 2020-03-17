package com.lyw.ruban.init.widgets.depend

import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.depend.DependInitContainer
import com.lyw.ruban.core.lazy.LazyInitContainer
import com.lyw.ruban.init.module.depend.ThreadListExternalDependModuleInit

/**
 * Created on  2020-03-17
 * Created by  lyw
 * Created for lazy depend module～
 */
open class LazyDependModule
constructor(
    moduleCode: Int,
    lazy: Boolean = false,
    aliasList: ArrayList<String>
) : LazyInitContainer<IDependInitObserver>(
    lazy, DependInitContainer(aliasList,
        ThreadListExternalDependModuleInit(
            moduleCode
        )
    )
)