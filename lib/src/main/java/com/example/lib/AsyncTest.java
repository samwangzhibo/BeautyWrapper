package com.example.lib;

/**
 * Created by zybang on 2018/1/10.
 */

public class AsyncTest {
    public static synchronized void  testClassAsync(){
        for (int i =0; i< 100; i++){
            System.out.println(Thread.currentThread() + " : testClassAsync : i ="+i);
        }
    }

    public static synchronized void  testClassAsync2(){
        for (int j =0; j< 100; j++){
            System.out.println(Thread.currentThread() + " : testClassAsync2 : j =" + j);
        }
    }

    public void  testClassAsync3(){
        for (int j =0; j< 100; j++){
            System.out.println(Thread.currentThread() + " : testClassAsync3 : k =" + j);
        }
    }

    public synchronized void  testClassAsync4(){
        for (int j =0; j< 100; j++){
            System.out.println(Thread.currentThread() + " : testClassAsync4 : m =" + j);
        }
    }
}
