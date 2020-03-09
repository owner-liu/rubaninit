package com.lyw.ruban.core

import com.lyw.ruban.core.depend.AbsDependInit

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for base depend observer proxy~
 */
open class BaseDependObserverProxy<T : IInitObserver>
    : BaseObserverProxy<T>() {

    var lock = Any()

    var mWaitToInitMap = hashMapOf<String, ArrayList<AbsDependInit<IDependInitObserver>>>()
}