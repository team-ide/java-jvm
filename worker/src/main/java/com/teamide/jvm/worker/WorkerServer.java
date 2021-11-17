package com.teamide.jvm.worker;


import com.alibaba.fastjson.JSONObject;


public class WorkerServer {

    private final JSONObject config;
    private final PlatformConfig platformConfig;
    private final PlatformDataWorker platformDataWorker = new PlatformDataWorker();
    private boolean started = false;

    private WorkerServer(JSONObject config) {
        this.config = config;
        this.platformConfig = createPlatformConfig(config);
    }

    private static WorkerServer workerServer;

    public static WorkerServer getInstance() {
        return workerServer;
    }

    public static void start(JSONObject config) throws Exception {
        if (workerServer == null) {
            workerServer = new WorkerServer(config);
            workerServer.started = true;
            workerServer.platformDataWorker.start();
        }
    }

    public static void stop() throws Exception {
        if (workerServer == null) {
            return;
        }
        workerServer.started = false;
        Thread.sleep(1000 * 5);
        workerServer = null;
    }

    public static boolean isStarted() {
        if (workerServer == null) {
            return false;
        }
        return workerServer.started;
    }

    private PlatformConfig createPlatformConfig(JSONObject config) {
        JSONObject platformConfig = new JSONObject();
        for (String key : config.keySet()) {
            if (key.startsWith("platform.")) {
                platformConfig.put(key.replace("platform.", ""), config.get(key));
            }
        }
        return platformConfig.toJavaObject(PlatformConfig.class);
    }

    public JSONObject getConfig() {
        return config;
    }

    public PlatformDataWorker getPlatformDataWorker() {
        return platformDataWorker;
    }

    public PlatformConfig getPlatformConfig() {
        return platformConfig;
    }

}
