package com.lyw.ruban.plugin

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class RubanPlugin : Plugin<Project> {
    override fun apply(project: Project) {
        println("RubanPlugin apply ~")
        /**
         * 1。每个module中自行添加plugin～
         */
        val appExtension = project.extensions.getByType(AppExtension::class.java)
        appExtension.registerTransform(AliasTransform(project))
    }
}
