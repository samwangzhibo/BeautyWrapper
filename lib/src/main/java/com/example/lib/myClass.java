package com.example.lib;

public class myClass {
    public static void main(String[] args){
       final AsyncTest a = new AsyncTest();
       final AsyncTest b = new AsyncTest();
       new Thread("Thread-0"){
           @Override
           public void run() {
               super.run();
               AsyncTest.testClassAsync();
           }
       }.start();
        new Thread("Thread-1"){
            @Override
            public void run() {
                super.run();
                b.testClassAsync3();
            }
        }.start();
    }
}
