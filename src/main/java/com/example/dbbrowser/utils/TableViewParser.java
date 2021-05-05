package com.example.dbbrowser.utils;

import com.example.dbbrowser.dto.Record;
import com.example.dbbrowser.dto.TableRecord;
import com.example.dbbrowser.exceptions.UnableLoadDbException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class TableViewParser {
    private static final String LOAD_TABLE_VIEW = "SELECT * FROM %s.%s";
    private static final String LIMIT = "LIMIT";
    private static final String ORDER_BY = "ORDER BY";

    public String createViewQuery(String schemaName, String tableName,
        Optional<String> limit, Optional<String> orderBy) {

        if (schemaName == null && schemaName.isEmpty()
            && tableName == null && tableName.isEmpty()) {
            UnableLoadDbException.UnableLoadDbException("Unable to upload table metadata. " +
                "Table %s or schema %s is missing.", tableName, schemaName);
        }

        StringBuilder query = new StringBuilder();
        query.append(String.format(LOAD_TABLE_VIEW, schemaName, tableName));

        orderBy.ifPresent(s -> query.append(" ").append(ORDER_BY).append(" ").append(s));

        limit.ifPresent(s -> query.append(" ").append(LIMIT).append(" ").append(s));

        return query.toString();
    }


    public List<TableRecord> parseView(ResultSet loadedDbInfo) {
        List<TableRecord> recordList = new ArrayList<>();

        try {
            List<String> columnNames = readResultSetColumnNames(loadedDbInfo);

            while (loadedDbInfo.next()) {
                TableRecord tableRecord = new TableRecord();

                for (String columnName : columnNames) {
                    Record value
                        = new Record(columnName,
                        loadedDbInfo.getString(columnName));
                    tableRecord.addRecord(value);
                }

                recordList.add(tableRecord);
            }
        } catch (SQLException ex) {
            UnableLoadDbException.UnableLoadDbException("Failed to upload table view. " + ex.getMessage());

        }

        return recordList;
    }

    private List<String> readResultSetColumnNames(ResultSet result) throws SQLException {
        List<String> columnNames = new ArrayList<>();

        ResultSetMetaData resultMetaData = result.getMetaData();

        for (int i = 1; i <= resultMetaData.getColumnCount(); i++) {
            columnNames.add(resultMetaData.getColumnName(i));
        }

        return columnNames;
    }
}
