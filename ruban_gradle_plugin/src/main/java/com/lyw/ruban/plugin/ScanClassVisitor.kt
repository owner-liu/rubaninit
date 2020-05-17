package com.lyw.ruban.plugin

import com.sun.org.apache.bcel.internal.generic.ARETURN
import jdk.internal.org.objectweb.asm.ClassVisitor
import jdk.internal.org.objectweb.asm.Label
import jdk.internal.org.objectweb.asm.MethodVisitor
import jdk.internal.org.objectweb.asm.Opcodes


/**
 * Created on  2020/5/7
 * Created by  lyw
 * Created for scan class visitor~
 */
class ScanClassVisitor
constructor(api: Int, cv: ClassVisitor) : ClassVisitor(api, cv) {

    //是否需要扫描方法～
    private var needScanMethod = false
    private var className: String? = null

    override fun visit(
        version: Int,
        access: Int,
        name: String?,
        signature: String?,
        superName: String?,
        interfaces: Array<out String>?
    ) {
        //对比 class～
        super.visit(version, access, name, signature, superName, interfaces)
        // TODO by LYW: 2020/5/15 通过接口 区别～
        if (superName?.equals("com/lyw/ruban/init/lib/LibInit") == true) {
            //过滤对应的class～
            className = name;
            needScanMethod = true
        }
    }

    override fun visitMethod(
        access: Int,
        name: String?,
        desc: String?,
        signature: String?,
        exceptions: Array<out String>?
    ): MethodVisitor {
        var methodVisitor = super.visitMethod(access, name, desc, signature, exceptions)
        if (needScanMethod && name?.equals("getAliasName") == true) {
            methodVisitor = ScanMethodVisitor(api, methodVisitor, className)
        }
        return methodVisitor
    }
}

class ScanMethodVisitor
constructor(api: Int, mv: MethodVisitor, var className: String?) : MethodVisitor(api, mv) {

    override fun visitInsn(opcode: Int) {
        println("RubanPlugin visitInsn:$opcode")
        super.visitInsn(opcode)
    }

    override fun visitMaxs(maxStack: Int, maxLocals: Int) {
        println("RubanPlugin visitMaxs:$maxStack,$maxLocals")
        super.visitMaxs(maxStack, maxLocals)
    }
}