package com.lyw.ruban.core

import com.lyw.ruban.core.depend.AbsDependInit

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for
 */
class BaseDependObserverProxy<T : IInitObserver>
    : BaseObserverProxy<T>() {

    var mWaitToInits = hashMapOf<String, ArrayList<AbsDependInit<T>>>()
}