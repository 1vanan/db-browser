package com.example.dbbrowser.service;

import com.example.dbbrowser.dto.Record;
import com.example.dbbrowser.dto.TableColumn;
import com.example.dbbrowser.dto.TableRecord;
import com.example.dbbrowser.exceptions.UnableLoadDbException;
import com.example.dbbrowser.utils.PostgressqlConnecter;
import com.example.dbbrowser.dto.ConnectionProperties;
import com.example.dbbrowser.dto.Table;
import com.example.dbbrowser.exceptions.PropertyNotFoundException;
import com.example.dbbrowser.repository.ConnectionPropertiesRepository;
import com.example.dbbrowser.utils.MetadataParser;
import com.example.dbbrowser.utils.TableViewParser;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionPropertiesServiceImpl implements ConnectionPropertiesService {
    private final ConnectionPropertiesRepository propertiesRepository;

    @Override
    public ConnectionProperties save(ConnectionProperties properties) {
        return propertiesRepository.save(properties);
    }

    @Override
    public List<ConnectionProperties> findAllProperties() {
        return propertiesRepository.findAll();
    }

    @Override
    public ConnectionProperties updateProperties(ConnectionProperties newProperties) {
        return propertiesRepository.findById(newProperties.getId())
            .map(properties -> {
                properties.setId(newProperties.getId());
                properties.setName(newProperties.getName());
                properties.setHostname(newProperties.getHostname());
                properties.setPort(newProperties.getPort());
                properties.setDbname(newProperties.getDbname());
                properties.setUsername(newProperties.getUsername());
                properties.setPassword(newProperties.getPassword());
                return propertiesRepository.save(properties);
            })
            .orElseThrow(() -> new PropertyNotFoundException("update failed on id: " + newProperties.getId()));
    }

    @Override
    public void deleteProperties(Long id) {
        propertiesRepository.findById(id)
            .orElseThrow(() -> new PropertyNotFoundException("deletion failed on id: " + id));
        propertiesRepository.deleteById(id);
    }
}
