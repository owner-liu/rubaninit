package com.lyw.ruban.core

import com.lyw.ruban.core.depend.AbsDependInit

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for init observer~
 */

interface IInitObserver {

    fun onCompleted(context: InitContext, aliasName: String)
}

interface IDependInitObserver :
    IInitObserver {

    fun onWaitToInit(
        context: InitContext,
        init: AbsDependInit<IDependInitObserver>,
        dependAliasName: String
    )
}