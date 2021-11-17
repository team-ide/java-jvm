package com.teamide.jvm.test;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;

@SpringBootApplication
@Component
public class ApplicationMain {


    public static void main(String[] args) throws Exception {
        System.out.println(ManagementFactory.getRuntimeMXBean().getName());
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Worker worker = new Worker();
                while (true) {
                    try {
                        worker.work();
                    } catch (Exception e) {

                    } finally {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }
    }

}
