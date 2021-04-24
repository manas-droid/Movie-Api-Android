package com.example.movieapp.data.network;


public class Resource<T> {
    public enum Status{
        SUCCESS,ERROR,LOADING
    }
    public final T data;
    public final String message;
    public final Status status;
    public Resource(Status status , T data, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public static <T> Resource<T> success(T data){
        return new Resource<>(Status.SUCCESS ,data, null);
    }

    public static <T> Resource<T> error (String msg , T data){
        return new Resource<>(Status.ERROR, data, msg);
    }

    public static <T> Resource<T> loading(T data){
        return new Resource<>(Status.LOADING ,data , null);
    }

}
