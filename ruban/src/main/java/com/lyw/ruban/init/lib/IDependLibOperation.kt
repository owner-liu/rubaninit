package com.lyw.ruban.init.lib

import com.lyw.ruban.init.widgets.DependLibInit

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for
 */
interface IDependLibOperation : ILibOperation {
    fun addDependLibInit(init: DependLibInit)

    fun addModuleDependAlias(moduleCode: Int, list: ArrayList<String>)
}