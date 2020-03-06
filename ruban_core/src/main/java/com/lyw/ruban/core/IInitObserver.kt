package com.lyw.ruban.core

/**
 * Created on  2020-03-05
 * Created by  lyw
 * Created for init observer~
 */

interface IInitObserver {

    fun onCompleted(context: InitContext, aliasName: String)
}