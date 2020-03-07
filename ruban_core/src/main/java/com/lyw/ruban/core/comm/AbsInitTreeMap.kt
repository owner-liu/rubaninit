package com.lyw.ruban.core.comm

import com.lyw.ruban.core.IInitMap
import com.lyw.ruban.core.IInitObserver
import java.util.*

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for abs init tree map~
 */
abstract class AbsInitTreeMap<T, U, Z : IInitObserver>
    : IInitMap<T, U, Z> {

    override fun put(key: T, value: U) {
        (getData() as TreeMap).put(key, value)
    }

}