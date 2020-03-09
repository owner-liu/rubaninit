package com.lyw.ruban.core.thread

import com.lyw.ruban.core.AbsInit
import com.lyw.ruban.core.IInitList
import com.lyw.ruban.core.IInitObserver

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for
 */
abstract class AbsThreadList<T : AbsInit, U : IInitObserver>
constructor(threadCode: Int) :
    AbsThreadInit<U>(threadCode), IInitList<T, U> {
}