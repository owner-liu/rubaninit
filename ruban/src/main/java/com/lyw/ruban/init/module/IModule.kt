package com.lyw.ruban.init.module

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for module 接口～
 */
interface IModule<T, U> {

    fun addInit(init: U)
}