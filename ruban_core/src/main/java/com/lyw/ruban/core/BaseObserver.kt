package com.lyw.ruban.core

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for base observer~
 */
open class BaseObserver {

    var initCount: Int = 0

    var mInitCompletedAliases = hashSetOf<String>()
}