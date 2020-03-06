package com.lyw.ruban.core.depend

import com.lyw.ruban.core.AbsBaseInit

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for abs base depend init~
 */
abstract class AbsBaseDependInit
constructor(private var absBaseDependInitAliases: ArrayList<String>) :
    AbsBaseInit<IDependInitObserver>()
    , IDepend {

    fun getAlias(): String {
        return this.javaClass.simpleName
    }

    override fun getDependAliased(): ArrayList<String> {
        return absBaseDependInitAliases
    }

    override fun refreshDependComplete(aliasName: String) {
        absBaseDependInitAliases.remove(aliasName)
    }

    override fun hasDepend(): Boolean {
        return absBaseDependInitAliases.isNotEmpty()
    }

    override fun getFirstDependAlias(): String {
        return absBaseDependInitAliases.first()
    }
}