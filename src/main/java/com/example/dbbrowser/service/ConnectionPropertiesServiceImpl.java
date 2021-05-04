package com.example.dbbrowser.service;

import com.example.dbbrowser.dto.ConnectionProperties;
import com.example.dbbrowser.exceptions.PropertyNotFoundException;
import com.example.dbbrowser.repository.ConnectionPropertiesRepository;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionPropertiesServiceImpl implements ConnectionPropertiesService {
    private final ConnectionPropertiesRepository propertiesRepository;

    @Override
    public ConnectionProperties save(ConnectionProperties properties) {
        // TODO: validate fields of newProperties, such as port etc

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

    @Override public List<String> findAllSchemas() {
        List<String> response = new ArrayList<>();
        List<ConnectionProperties> properties = propertiesRepository.findAll();
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
        properties.forEach(prop -> {
            dataSourceBuilder.url(prop.getHostname());
            dataSourceBuilder.username(prop.getUsername());
            dataSourceBuilder.password(prop.getPassword());
            try {
                Connection connection = dataSourceBuilder.build().getConnection();
                DatabaseMetaData databaseMetaData = connection.getMetaData();
                ResultSetMetaData schemas = databaseMetaData.getSchemas().getMetaData();
                for (int i = 0; i < schemas.getColumnCount(); i++) {
                    response.add(schemas.getSchemaName(i));
                }
            }
            catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        return response;
    }
}
