package com.lyw.ruban.core

import com.lyw.ruban.core.depend.BaseDependInit

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for init observer~
 */

interface IInitObserver {

    fun onCompleted(context: InitContext, tag: String)
}