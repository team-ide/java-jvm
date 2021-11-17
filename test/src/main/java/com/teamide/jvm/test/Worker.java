package com.teamide.jvm.test;

/**
 * @description: TODO 类描述
 * @author: 朱亮
 * @date: 2021/10/27 10:49
 **/
public class Worker {

    public long work() throws Exception {
        long max = 1000, min = 100;
        long ran = (long) (Math.random() * (max - min) + min);
//        System.out.println("Worker:work:ran:" + ran + ",start");
        Thread.sleep(ran);
//        System.out.println("Worker:work:ran:" + ran + ",end");
        return ran;
    }
}
