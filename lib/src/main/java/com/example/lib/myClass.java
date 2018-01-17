package com.example.lib;

public class myClass {
    public static void main(String[] args){
       final AsyncTest a = new AsyncTest();
       final AsyncTest b = new AsyncTest();
       new Thread("Thread-0"){
           @Override
           public void run() {
               super.run();
//               a.testClassAsync2();
//               b.testClassAsync4();
               a.testClassAsync4();
           }
       }.start();
        new Thread("Thread-1"){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                a.testClassAsync5();
//                b.testClassAsync();
                b.testClassAsync3();
            }
        }.start();
    }
}
