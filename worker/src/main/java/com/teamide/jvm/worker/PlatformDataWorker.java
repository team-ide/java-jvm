package com.teamide.jvm.worker;

import com.google.common.collect.Queues;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @description: TODO 类描述
 * @author: 朱亮
 * @date: 2021/10/27 17:16
 **/
public class PlatformDataWorker {


    private LinkedBlockingQueue<PlatformData> queue = new LinkedBlockingQueue();


    public void push(PlatformData data) {
        try {
            queue.put(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() throws Exception {
        new Thread(() -> {
            try {
                System.out.println("platform url:" + WorkerServer.getInstance().getPlatformConfig().getUrl());
                while (WorkerServer.isStarted()) {
                    List<PlatformData> datas = new ArrayList<>(10);
                    Queues.drain(queue, datas, 10, 1, TimeUnit.SECONDS);
                    upload(datas);
                }
            } catch (Exception e) {
            }
        }).start();
    }


    private void upload(List<PlatformData> datas) {
        if (!WorkerServer.isStarted()) {
            return;
        }
        if (datas == null || datas.size() == 0) {
            return;
        }

    }
}
