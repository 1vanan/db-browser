package com.example.dbbrowser.controller;

import com.example.dbbrowser.dto.ConnectionProperties;
import java.util.List;

public interface BrowserApi {
    List<ConnectionProperties> listProperties();

    ConnectionProperties addProperties(ConnectionProperties properties);

    ConnectionProperties updateProperties(ConnectionProperties properties);

    void deleteProperties(Long id);

    public List<String> listSchemas();
}
