package com.lyw.ruban.core.depend

import com.lyw.ruban.core.IInitMap
import com.lyw.ruban.core.IInitObserver

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for abs depend map~
 */
abstract class AbsDependMap<T, U, Z : IInitObserver>
constructor(aliasList: ArrayList<String>) : AbsDependInit<Z>(aliasList),
    IInitMap<T, U, Z> {
}