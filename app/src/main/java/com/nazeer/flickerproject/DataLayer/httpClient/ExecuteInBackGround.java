package com.nazeer.flickerproject.DataLayer.httpClient;

import java.io.IOException;

public interface ExecuteInBackGround<T> {
    T execute() throws Exception;
}
