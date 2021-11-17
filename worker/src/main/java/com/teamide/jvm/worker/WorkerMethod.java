package com.teamide.jvm.worker;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;


/**
 * @description: TODO 类描述
 * @author: 朱亮
 * @date: 2021/10/29 14:53
 **/
public class WorkerMethod {

    public static void doMethod(CtClass cl, CtBehavior method) throws NotFoundException, CannotCompileException {
        System.out.println("WorkerMethod.doMethod:" + cl.getName() + "." + method.getName());
        method.insertBefore("System.out.println(\"" + cl.getName() + "." + method.getName() + " start\");");
        method.insertAfter("System.out.println(\"" + cl.getName() + "." + method.getName() + " end\");");
    }
}
