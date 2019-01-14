package com.kunpenggu.demo.rabbitmq;

import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

//@Component
public class Receiver {
    private CountDownLatch latch = new CountDownLatch(1);

    public void receiveMessage(String msg){
        System.out.println("Received < " + msg + " >");
        latch.countDown();
    }

    public CountDownLatch getLatch(){
        return latch;
    }
}
