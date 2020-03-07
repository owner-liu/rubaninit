package com.lyw.ruban.core

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for init observer~
 */

interface IInitObserver {

    fun onCompleted(context: InitContext, aliasName: String)
}

interface IDependInitObserver<T : IInitObserver> :
    IInitObserver {

    fun onWaitToInit(context: InitContext, init: IInit<T>, dependAliasName: String)
}