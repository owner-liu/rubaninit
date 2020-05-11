package com.lyw.ruban.core

import java.util.concurrent.CopyOnWriteArrayList

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for base observer~
 */
open class BaseObserver {

    var initCount: Int = 0

    var mInitCompletedAliases = arrayListOf<String>()
}