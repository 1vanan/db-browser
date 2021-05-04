package com.example.dbbrowser.configuration;

import com.example.dbbrowser.dto.ConnectionProperties;
import com.example.dbbrowser.exceptions.UnableConnectionException;
import java.sql.Connection;
import java.sql.SQLException;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.stereotype.Component;

@Component
public class MySqlConnecter {

    public Connection createConnection(ConnectionProperties properties) {

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.driverClassName("com.mysql.cj.jdbc.Driver");

        dataSourceBuilder.url("jdbc:mysql://" + properties.getHostname() + ":"
            + properties.getPort() + "/" + properties.getDbname());
        dataSourceBuilder.username(properties.getUsername());
        dataSourceBuilder.password(properties.getPassword());

        Connection connection = null;
        try {
            connection = dataSourceBuilder.build().getConnection();
        }
        catch (SQLException throwables) {
            UnableConnectionException.UnableConnectionException(properties);
        }

        return connection;
    }
}