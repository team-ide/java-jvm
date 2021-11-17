package com.teamide.jvm.worker;


import com.alibaba.fastjson.JSONObject;

import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;

import java.security.ProtectionDomain;


public class Worker {


    public static void work(String arg, Instrumentation _inst) throws Exception {
        arg = arg == null ? "" : arg;
        JSONObject json = JSONObject.parseObject(arg.trim());
        String command = json.getString("command");
        System.out.println("jvm worker command:" + command);
        switch (command) {
            case "start":
                start(json, _inst);
                break;
            case "stop":
                stop();
                break;
            default:
                break;
        }
    }

    private static void start(JSONObject json, Instrumentation _inst) throws Exception {
        WorkerServer.start(json);
    }

    private static void stop() throws Exception {
        WorkerServer.stop();

    }

}
