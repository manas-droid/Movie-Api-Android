package com.example.movieapp.Commons;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ExecutorHelper {
    private static ExecutorService instance;
    public static synchronized ExecutorService getSingleThreadExecutorInstance(){
        if(instance == null){
            instance = Executors.newSingleThreadExecutor();
        }
        return instance;
    }
}