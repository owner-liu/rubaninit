package com.lyw.ruban.core

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for base observer proxy~
 */
open class BaseObserverProxy<T : IInitObserver> : BaseObserver() {
    var mObserver: T? = null
}