package com.lyw.ruban.init.widgets

import com.lyw.ruban.core.IInitObserver
import com.lyw.ruban.core.depend.DependInitContainer
import com.lyw.ruban.init.lib.LibInit

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for depend lib init
 */
class DependThreadLibInit
constructor(
    aliasList: ArrayList<String>,
    var dependLibInit: ThreadLibInit
) : DependInitContainer<IInitObserver>(aliasList, dependLibInit) {

    override fun getAliasName(): String {
        return dependLibInit.getAliasName()
    }
}