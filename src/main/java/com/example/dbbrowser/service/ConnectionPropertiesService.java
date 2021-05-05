package com.example.dbbrowser.service;

import com.example.dbbrowser.dto.ConnectionProperties;
import com.example.dbbrowser.dto.Table;
import com.example.dbbrowser.dto.TableColumn;
import com.example.dbbrowser.dto.TableRecord;
import java.util.List;
import java.util.Optional;

public interface ConnectionPropertiesService {

    ConnectionProperties save(ConnectionProperties properties);

    List<ConnectionProperties> findAllProperties();

    ConnectionProperties updateProperties(ConnectionProperties properties);

    void deleteProperties(Long id);
}
