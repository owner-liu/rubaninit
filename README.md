# RubanInit
Android App 相关库的初始化管理

[![](https://jitpack.io/v/owner-liu/rubaninit.svg)](https://jitpack.io/#owner-liu/rubaninit)

#### 说明：

- v1.0.1-rc5 去除自定义 alias。alias 统一为类名，需要添加 混淆配。
- v1.0.1-rc6 针对于相关处理完成监听，使用软引用进行存储。
- v1.0.1-rc14 增加初始化中的标记位，增加代码调用初始化完成逻辑（初始化过程中需要等待异步回调），优化api
- v1.0.1-rc16 代码优化，增加 NetWorkConfigInit 类,针对于 网络配置的初始化，可以手动调用 notifyFinished() 来设置初始化完成状态
- v1.0.1-rc17 增加ContentProvider 方式 初始化配置，使用可以参考 RubanInitializationProviderImpl 类，记得androidmanifest.xml中配置，authorities 记得配置不同的。 

Add it in your root build.gradle at the end of repositories:
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

Add the dependency
```
	dependencies {
	        implementation 'com.github.owner-liu:rubaninit:version'
	}
```

proguard-rules.pro
```
# RubanInit 避免混淆相关初始化类名，防止有依赖时，没办法正确匹配到～ 
-keep public class * extends com.lyw.ruban.init.lib.LibInit
```

#### 示例代码：
##### App module 统一配置
```
 AppInitManager.apply {
            addLibInit(TestThreadALib())
            addLibInit(TestThreadBLib())
            addLibInit(TestThreadCLib())

            configModule(ModuleConfig(1, false, arrayListOf("2")))

            addModuleCompletedListener(
                hashSetOf(1),
                object : ICompleteListener {
                    override fun onCompleted() {
                        Log.i("ruban", "监听到相关module 已完成～")

                        initializeLazyAll()
                    }
                })

            addInitCompletedListener(1, "TestThreadALib", object : ICompleteListener {
                override fun onCompleted() {
                    Log.i("ruban", "监听到指定lib初始化完成～")
                }
            })

            addAppCompletedListener(object : ICompleteListener {
                override fun onCompleted() {
                    Log.i("ruban", "监听到全部初始化完成～")
                }

            })

            setLogger(CustomizationLogger())

            initialize(application, isDebug);
        }
```
```
class TestThreadALib : LibInit(1, ConstantsForCore.THREAD_ASYNC, arrayListOf()) {
    //当前init别名，如果其他的库有依赖于当前库，需要填写，防止混淆时，无法找到对应的init～
    override fun getAliasName(): String {
        return "TestThreadALib"
    }
    //初始化逻辑编写地方
    override fun doInit(context: InitContext, observer: IInitObserver) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            Log.i("ruban", "err-线程异常-init:${getAliasName()}")
        }
    }
}
```
```
class CustomizationLogger : ILogger {
    override fun d(tag: String, msg: String) {

    }

    override fun e(tag: String, msg: String) {
    }

    override fun i(tag: String, msg: String) {
    }

    override fun v(tag: String, msg: String) {
    }

    override fun w(tag: String, msg: String) {
    }

}
```
##### contentProvider 方式配置
```
class RubanInitializationProviderImpl : RubanInitializationProvider() {

    override fun initLib(): ArrayList<LibInit> {
        return arrayListOf(TestThreadALib())
    }

    override fun isLazy(): Boolean {
        return false
    }

    override fun getDepends(): ArrayList<String> {
        return arrayListOf("2")
    }
}



<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.lyw.rubaninit">

    <application
       ...>
       
        <provider
            android:name=".test.RubanInitializationProviderImpl"
            android:authorities="${applicationId}.provider1"
            android:exported="false" />
        <provider
            android:name=".test.RubanInitializationProviderImpl2"
            android:authorities="${applicationId}.provider2"
            android:exported="false" />
    </application>

</manifest>


```


#### 说明：
##### 1.初始化
      1.module～
              1.库的初始化以module为界
              2.module 可以设置延迟加载～
              3.module 之间可以存在依赖关系～
      2.lib
              1.lib 可以设置指定线程执行～
              2.lib 之间可以存在 module内的依赖关系

 ##### 2.监听
      1.可以设置单个或者多个module的初始化完成监听～
      2.可以设置对应module内的指定Init的初始化完成监听～
      3.可以设置所有的module初始化完成监听～
      4.针对于延迟初始化的项目，需要单独添加回调监听

 ##### 3.针对于延迟初始化～
      1.延迟初始化的库之间禁止存在依赖关系,当依赖库彼此之前存在依赖，会加大复杂性，在处理器复杂性时，本身会带来性能问题。
      
 ##### 4.初始化过程中添加module～
      1.初始化开始后，新增加的初始化module，一律为Lazy，需要单独添加监听。

 ##### 5.优化：
      1.插队策略
      2.异步并发
      3.初始化（依赖结果的，通过addInitCompletedListener，初始化需要的，直接添加depend～）
      4.不使用锁，通过其自身的优先级来去除掉加速锁带来的性能消耗

![](https://github.com/owner-liu/pic/blob/master/ruban_uml.jpg)
