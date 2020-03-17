package com.lyw.ruban.init.widgets.depend

import com.lyw.ruban.core.IDependInitObserver
import com.lyw.ruban.core.depend.DependInitContainer
import com.lyw.ruban.init.module.depend.ThreadListExternalDependModuleInit

/**
 * Created on  2020-03-17
 * Created by  lyw
 * Created for depend module～
 */
class DependModule
constructor(
    moduleCode: Int,
    aliasList: ArrayList<String> = arrayListOf()
) : DependInitContainer<IDependInitObserver>(
    aliasList,
    ThreadListExternalDependModuleInit(moduleCode)
)
