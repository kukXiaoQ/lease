package com.situ.ws.util;

public class LeaseThreadLocal {

    private static final ThreadLocal<Integer> threadLocal = new ThreadLocal<>();


    public static void set(Integer str){
        threadLocal.set(str);
    }

    public static Integer get(){
        return threadLocal.get();
    }
}
