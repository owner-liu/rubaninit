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
 * 1。module之间存在依赖：？？？？？+DependInitContainer+ModuleInit
 * 2。module不存在依赖：AppInit+ModuleInit
 * 3。线程列表之间存在依赖：DependInitContainer+AbsThreadDependList
 * 4。线程列表之间不存在依赖：AbsThreadDependLis
 * 5。跨线程之间存在依赖：？？？+AbsThreadExternalDependList
 * 6。线程内存在依赖：ModuleInit+AbsThreadInternalDependList
 */
class AppInitManager {
}