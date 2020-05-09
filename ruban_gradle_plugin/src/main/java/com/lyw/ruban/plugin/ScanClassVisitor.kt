package com.lyw.ruban.plugin

import jdk.internal.org.objectweb.asm.ClassVisitor


/**
 * Created on  2020/5/7
 * Created by  lyw
 * Created for scan class visitor~
 */
class ScanClassVisitor
constructor(api: Int, cv: ClassVisitor) : ClassVisitor(api, cv) {

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        println("RubanPlugin visit version:$version,access:$access,name:$name,signature:$signature,superName:$superName,interfaces:$interfaces")
    }
}