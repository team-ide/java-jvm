package com.teamide.jvm.platform;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.tools.attach.VirtualMachine;

import java.io.File;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class ApplicationMain {
    static File agentJarFile = new File("C:\\Workspace\\code\\team-ide\\java-jvm\\agent\\target\\java-jvm-agent.jar");
    static File workerJarFile = new File("C:\\Workspace\\code\\team-ide\\java-jvm\\worker\\target\\java-jvm-worker.jar");

    public static void main(String[] args) throws Exception {
        VirtualMachine virtualMachine = VirtualMachine.attach("15628");
        System.out.println("------------SystemProperties-----------");
        virtualMachine.getSystemProperties().forEach((name, value) -> {
            System.out.println(name + ":" + value);
        });
        System.out.println("------------AgentProperties-----------");
        virtualMachine.getAgentProperties().forEach((name, value) -> {
            System.out.println(name + ":" + value);
        });
        JSONObject config = new JSONObject();
        config.put("command", "start");
        virtualMachine.loadAgent(agentJarFile.getAbsolutePath(), getArg(config));
        virtualMachine.detach();
    }

    public static String getArg(JSONObject config) throws Exception {
        StringBuffer arg = new StringBuffer();
        arg.append(workerJarFile.toURI().getPath());
        arg.append("\t");
        config.put("platform.url", "http://127.0.0.1:18010/java-jvm");
        arg.append(config.toJSONString());
        return URLEncoder.encode(arg.toString(), "utf-8");
    }
}
