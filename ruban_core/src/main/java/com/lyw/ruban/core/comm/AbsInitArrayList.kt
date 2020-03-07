package com.lyw.ruban.core.comm

import android.util.Log
import com.lyw.ruban.core.IInitList
import com.lyw.ruban.core.IInitObserver

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for abs init ArrayList~
 */
abstract class AbsInitArrayList<T, U : IInitObserver> :
    IInitList<T, U> {

    override fun add(init: T) {
        (getData() as ArrayList).add(init)
    }
}