package com.lyw.ruban.core.depend

import com.lyw.ruban.core.AbsInitGroup

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for base depend init group~
 */
abstract class AbsDependInitGroup
//记录相关的依赖别名～
constructor(
    private var AbsDependInitGroupAliases: ArrayList<String>
) : AbsInitGroup<IDependGroupObserver>()
    , IDepend {

    final override fun getDependAliased(): ArrayList<String> {
        return this.AbsDependInitGroupAliases
    }

    final override fun refreshDependComplete(aliasName: String) {
        AbsDependInitGroupAliases.remove(aliasName)
    }

    final override fun hasDepend(): Boolean {
        return AbsDependInitGroupAliases.isNotEmpty()
    }

    final override fun getFirstDependAlias(): String {
        return AbsDependInitGroupAliases.first()
    }

}