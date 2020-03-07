package com.lyw.ruban.init.module

import com.lyw.ruban.core.AbsBaseInit
import com.lyw.ruban.core.IInitObserver

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for module 接口～
 */
interface IModule<T, U> {

    fun addInit(init: U)

    fun addThreadList(list: T)
}