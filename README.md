# RubanInit
Android App 相关库的初始化管理

[![](https://jitpack.io/v/owner-liu/RubanInit.svg)](https://jitpack.io/#owner-liu/RubanInit)

#### 说明：
##### 1。初始化
      1。module～
              1。库的初始化以module为界
              2。module 可以设置延迟加载～
              3。module 之间可以存在依赖关系～
      2。lib
              1。lib 可以设置指定线程执行～
              2。lib 之间可以存在 module内的依赖关系

 ##### 2。监听
      1。可以设置单个或者多个module的初始化完成监听～
      2. 可以设置对应module内的指定Init的初始化完成监听～
      3。可以设置所有的module初始化完成监听（包含延迟初始化的module）～

 ##### 3。针对于延迟初始化～
      1。延迟初始化的库之间禁止存在依赖关系～

 ##### 4.优化：
 1。插队策略
 2。异步并发
 3. 初始化（依赖结果的，通过addInitCompletedListener，初始化需要的，直接添加depend～）

![](https://github.com/owner-liu/pic/blob/master/ruban_uml.jpg)
