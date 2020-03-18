package com.lyw.ruban

import com.lyw.ruban.core.InitContext

/**
 * Created on  2020-03-06
 * Created by  lyw
 * Created for app 管理～
 *
 * 思考个问题：
 * 1.相关库初始化成功后，相关类 是否还有存在的必要？
 *
 * 注：
 * 1。库之间的依赖提供跨线程，不提供跨Module依赖
 * 2。Module延迟初始化，其依赖库不允许设置为延迟初始化～
 *
 * 提供的功能：
 * App级别：
 * 1。提供Module间依赖～
 * 2。提供Module初始化查询～ //待实现～
 * 3。提供Module延迟初始化，初始化设置为延迟的Module～ //待实现～
 * Module级别：
 * 1。提供线程切换，不提供依赖功能：ModuleInit
 * 2。提供线程切换功能，提供线程集合内的依赖：ThreadListInternalDependModuleInit
 * 2。提供线程切换功能，提供module内的依赖：ThreadListExternalDependModuleInit
 *
 *
 * 针对于 延迟上报～
 * 1。初始化时，如果时延迟上报，则忽略该库～
 * 2。如果时因为depend 触发延迟上报～，则忽略 延迟上报的 标记位～
 *
 * 说明：
 * 1。指定线程，无相关依赖，可延迟初始化 module～
 * 2。指定线程，存在依赖（module内，module间），可延迟初始化（延迟初始化module不允许依赖延迟初始化的module～）。
 */
class AppInitManager {

    private var mContext: InitContext? = null




}