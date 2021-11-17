package com.teamide.jvm.worker;

import javassist.*;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * @description: TODO 类描述
 * @author: 朱亮
 * @date: 2021/10/29 13:53
 **/
public class FirstAgent {

    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        if (!className.startsWith("com/teamide/")) {
            return null;
        }
        byte[] transformed = null;
        System.out.println("Transforming " + className);
        ClassPool pool = ClassPool.getDefault();
        CtClass cl = null;
        try {
            cl = pool.makeClass(new java.io.ByteArrayInputStream(
                    classfileBuffer));
            if (cl.isInterface() == false) {
                CtBehavior[] methods = cl.getDeclaredBehaviors();
                for (int i = 0; i < methods.length; i++) {
                    if (methods[i].isEmpty() == false) {
                        doMethod(cl, methods[i]);
                    }
                }
                transformed = cl.toBytecode();
            }
        } catch (Exception e) {
            System.err.println("Could not instrument  " + className
                    + ",  exception : " + e.getMessage());
        } finally {
            if (cl != null) {
                cl.detach();
            }
        }
        return transformed;
    }

    private void doMethod(CtClass cl, CtBehavior method) throws NotFoundException, CannotCompileException {
        method.insertBefore("System.out.println(\"" + cl.getName() + "." + method.getName() + " start\");");
        method.insertAfter("System.out.println(\"" + cl.getName() + "." + method.getName() + " end\");");
//        method.instrument(new ExprEditor() {
//            public void edit(MethodCall m) throws CannotCompileException {
//                m.replace("{ long stime = System.nanoTime(); $_ = $proceed($$); System.out.println(\""
//                        + m.getClassName() + "." + m.getMethodName()
//                        + ":\"+(System.nanoTime()-stime));}");
//            }
//        });
    }
}
