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

    final override fun getDependAliased(): ArrayList<String> {
        return absBaseDependInitAliases
    }

    final override fun refreshDependComplete(aliasName: String) {
        absBaseDependInitAliases.remove(aliasName)
    }

    final override fun hasDepend(): Boolean {
        return absBaseDependInitAliases.isNotEmpty()
    }

    final override fun getFirstDependAlias(): String {
        return absBaseDependInitAliases.first()
    }
}