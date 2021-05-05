package com.example.dbbrowser.service;

import com.example.dbbrowser.dto.ConnectionProperties;
import com.example.dbbrowser.dto.Table;
import com.example.dbbrowser.dto.TableColumn;
import com.example.dbbrowser.dto.TableRecord;
import com.example.dbbrowser.exceptions.PropertyNotFoundException;
import com.example.dbbrowser.exceptions.UnableLoadDbException;
import com.example.dbbrowser.repository.ConnectionPropertiesRepository;
import com.example.dbbrowser.utils.MetadataParser;
import com.example.dbbrowser.utils.PostgressqlConnecter;
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
public class DbBrowsingServiceImpl implements DbBrowsingService {
    private final ConnectionPropertiesRepository propertiesRepository;

    @Autowired
    private final PostgressqlConnecter postgressqlConnecter;

    @Autowired
    private final MetadataParser metadataParser;

    @Autowired
    private final TableViewParser viewParser;

    @Override public List<String> findAllSchemas(Long id) {
        List<String> response = new ArrayList<>();
        ConnectionProperties prop = getPropertiesIfExist(id);

        try (Connection connection = postgressqlConnecter.createConnection(prop)) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSetMetaData schemas = databaseMetaData.getSchemas().getMetaData();
            for (int i = 0; i < schemas.getColumnCount(); i++) {
                response.add(schemas.getSchemaName(i));
            }
        }
        catch (SQLException throwables) {
            log.error("Unable to get schema on connection: " + prop);
            throwables.printStackTrace();
        }
        return response;
    }

    @Override public List<Table> findAllTables(Long id, Optional<String> schema) {
        List<Table> response = new ArrayList<>();
        ConnectionProperties prop = getPropertiesIfExist(id);

        try (Connection connection = postgressqlConnecter.createConnection(prop)) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet tables;
            if (schema.isPresent()) {
                tables = databaseMetaData.getTables(null, schema.get(), null,
                    new String[] {"TABLE"});
            }
            else {
                tables = databaseMetaData.getTables(null, null, null,
                    new String[] {"TABLE"});
            }
            response.addAll(metadataParser.readTables(tables));
        }
        catch (SQLException throwables) {
            UnableLoadDbException.UnableLoadDbException("Failed to get table columns metadata on connection " +
                "with properties: " + id);
        }
        return response;
    }

    @Override public List<TableColumn> listTableColumns(Long id, Optional<String> schema, Optional<String> table) {
        List<TableColumn> response = new ArrayList<>();
        ConnectionProperties prop = getPropertiesIfExist(id);

        try (Connection connection = postgressqlConnecter.createConnection(prop)) {
            DatabaseMetaData databaseMetaData = connection.getMetaData();
            ResultSet columns = databaseMetaData.getColumns(
                null, schema.isPresent() ? schema.get() : null,
                table.isPresent() ? table.get() : null, null);

            response = metadataParser.readColumns(columns);
        }
        catch (SQLException throwables) {
            UnableLoadDbException.UnableLoadDbException("Failed to get table columns metadata on connection " +
                "with properties: " + id);

        }
        return response;
    }

    @Override public List<TableRecord> listTableRecords(Long id, String schema, String table, Optional<String> orderBy,
        Optional<String> limit) {
        List<TableRecord> tableRecords = new ArrayList<>();
        String viewQuery = viewParser.createViewQuery(schema, table, limit, orderBy);
        ConnectionProperties prop = getPropertiesIfExist(id);

        try (Connection connection = postgressqlConnecter.createConnection(prop)) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(viewQuery);
            tableRecords.addAll(viewParser.parseView(result));
        }
        catch (SQLException throwables) {
            UnableLoadDbException.UnableLoadDbException("Failed to get table columns metadata on connection " +
                "with properties: " + id);
        }

        return tableRecords;
    }

    private ConnectionProperties getPropertiesIfExist(Long id) {
        return propertiesRepository.findById(id).orElseGet(() -> {
            PropertyNotFoundException.PropertyNotFoundException(id.toString());
            return null;
        });
    }
}
