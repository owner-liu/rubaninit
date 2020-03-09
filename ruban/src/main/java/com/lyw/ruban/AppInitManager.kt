package com.lyw.ruban

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for app 管理～
 *
 * 思考个问题：
 * 1.相关库初始化成功后，相关类 是否还有存在的必要？
 *
 * 待实现功能：
 * 1。延迟初始化功能～
 * 2。获取指定module 是否初始化完成，BaseObserver 中添加回调管理～
 *
 * 注：库之间的依赖关系只允许 到module级别～，
 * 提供的功能：
 * 1。提供线程切换，不提供依赖功能：ModuleInit
 * 2。提供线程切换功能，提供线程集合内的依赖：ThreadListInternalDependModuleInit
 * 2。提供线程切换功能，提供module内的依赖：ThreadListExternalDependModuleInit
 */
class AppInitManager {
}