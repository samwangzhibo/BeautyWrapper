package com.example.lib;

/**
 * Created by zybang on 2018/1/10.
 */

public class AsyncTest {
    public static synchronized void  testClassAsync(){
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for (int i =0; i< 100; i++){
            System.out.println(Thread.currentThread() + " : testClassAsync : i ="+i);
        }
    }

    public static synchronized void  testClassAsync2(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int j =0; j< 100; j++){
            System.out.println(Thread.currentThread() + " : testClassAsync2 : j =" + j);
        }
    }

    public synchronized void  testClassAsync3(){
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        for (int j =0; j< 100; j++){
            System.out.println(Thread.currentThread() + " : testClassAsync3 : k =" + j);
        }
    }

    public synchronized void  testClassAsync4(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int j =0; j< 100; j++){
            System.out.println(Thread.currentThread() + " : testClassAsync4 : m =" + j);
        }
    }

    public void  testClassAsync5(){
        for (int j =0; j< 100; j++){
            System.out.println(Thread.currentThread() + " : testClassAsync6 : m =" + j);
        }
    }
}
