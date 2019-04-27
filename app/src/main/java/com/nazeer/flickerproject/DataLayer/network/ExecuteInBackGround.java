package com.nazeer.flickerproject.DataLayer.network;

public interface ExecuteInBackGround<T> {
    T execute() throws Exception;
}
