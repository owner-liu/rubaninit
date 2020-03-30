package com.lyw.ruban.core.depend

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IDepend
import com.lyw.ruban.core.IInitObserver

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for abs base depend init~
 */
abstract class AbsDependInit<T : IInitObserver>
constructor(private var aliasList: ArrayList<String>) :
    AbsBaseInit<T>()
    , IDepend {

    final override fun getDependAliased(): ArrayList<String> {
        return aliasList
    }

    final override fun refreshDependComplete(aliasName: String) {
        aliasList.remove(aliasName)
    }

    final override fun hasDepend(): Boolean {
        return aliasList.isNotEmpty()
    }

    final override fun getFirstDependAlias(): String {
        return aliasList.first()
    }

    final override fun addAliasList(list: ArrayList<String>) {
        aliasList.addAll(list)
    }
}