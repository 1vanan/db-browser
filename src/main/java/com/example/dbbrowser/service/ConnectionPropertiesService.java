package com.example.dbbrowser.service;

import com.example.dbbrowser.dto.ConnectionProperties;
import java.util.List;

public interface ConnectionPropertiesService {

    ConnectionProperties save(ConnectionProperties properties);

    List<ConnectionProperties> findAllProperties();

    ConnectionProperties updateProperties(ConnectionProperties properties);

    void deleteProperties(Long id);

    List<String> findAllSchemas();


}
