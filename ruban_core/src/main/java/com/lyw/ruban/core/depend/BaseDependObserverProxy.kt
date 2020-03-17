package com.lyw.ruban.core.depend

import com.lyw.ruban.core.BaseObserverProxy
import com.lyw.ruban.core.IDependInitObserver

/**
 * Created on  2020-03-09
 * Created by  lyw
 * Created for base depend observer proxy~
 */
open class BaseDependObserverProxy
    : BaseObserverProxy<IDependInitObserver>() {

    var mWaitToInitMap = hashMapOf<String, ArrayList<AbsDependInit<IDependInitObserver>>>()
}