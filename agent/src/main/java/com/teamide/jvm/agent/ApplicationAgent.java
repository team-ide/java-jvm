package com.teamide.jvm.agent;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.NotFoundException;
import sun.misc.ClassLoaderUtil;

import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLDecoder;
import java.security.ProtectionDomain;

/**
 * @description: TODO 类描述
 * @author: 朱亮
 * @date: 2021/10/27 11:02
 **/
public class ApplicationAgent {


    public static void premain(String arg, Instrumentation inst) throws Exception {
        main(arg, inst);
    }

    public static void agentmain(String arg, Instrumentation inst) throws Exception {
        main(arg, inst);
    }

    private static WorkerClassloader workerClassloader;

    private static Object lock = new Object();

    private static void main(String arg, final Instrumentation inst) throws Exception {
        if (arg == null) {
            arg = "";
        }
        arg = URLDecoder.decode(arg, "utf-8");
        String workerJar = null;
        if (arg.indexOf("\t") > 0) {
            workerJar = arg.substring(0, arg.indexOf("\t"));
            arg = arg.substring(arg.indexOf("\t") + 1);
        }
        if (workerJar == null) {
            throw new RuntimeException("worker jar is null");
        }
        File workerJarFile = new File(workerJar);
        if (!workerJarFile.exists() || !workerJarFile.isFile()) {
            throw new RuntimeException("worker jar[" + workerJarFile.toURI().getPath() + "] does not exist");
        }
        if (workerClassloader == null) {
            synchronized (lock) {
                if (workerClassloader == null) {
                    workerClassloader = new WorkerClassloader(new URL[]{workerJarFile.toURI().toURL()});
                    ClassFileTransformer trans = new AgentClassTransformer();
                    inst.addTransformer(trans);
                }
            }
        }
        if (workerClassloader == null) {
            throw new RuntimeException("agent class loader does not exist");
        }

        Class<?> workerClass = workerClassloader.loadClass("com.teamide.jvm.worker.Worker");
        Method workMethod = workerClass.getMethod("work", new Class<?>[]{String.class, Instrumentation.class});
        workMethod.invoke(null, new Object[]{arg, inst});
        if (arg.indexOf("\"command\":\"stop\"") >= 0) {
            try {
                ClassLoaderUtil.releaseLoader(workerClassloader);
                workerClassloader = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void doMethod(CtClass cl, CtBehavior method) throws NotFoundException, CannotCompileException {
        try {
            Class<?> workerClass = workerClassloader.loadClass("com.teamide.jvm.worker.WorkerMethod");
            Method workMethod = workerClass.getMethod("doMethod", new Class<?>[]{CtClass.class, CtBehavior.class});
            workMethod.invoke(null, new Object[]{cl, method});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
