package com.lyw.ruban.core

/**
 * Created on  2020-03-08
 * Created by  lyw
 * Created for abs base init
 */
abstract class AbsBaseInit<T : IInitObserver>
    : AbsInit(),
    IInit<T> {
}