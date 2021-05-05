package com.example.dbbrowser.utils;

import com.example.dbbrowser.dto.Table;
import com.example.dbbrowser.dto.TableColumn;
import com.example.dbbrowser.exceptions.UnableLoadDbException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class MetadataParser {
    private static final String SCHEMA_NAME = "TABLE_SCHEM";
    private static final String CATALOG_NAME = "TABLE_CAT";
    private static final String TABLE_NAME = "TABLE_NAME";
    private static final String TABLE_TYPE = "TABLE_TYPE";
    private static final String REMARKS = "REMARKS";
    private static final String COLUMN_NAME = "COLUMN_NAME";
    private static final String DATA_TYPE = "DATA_TYPE";
    private static final String POSITION = "ORDINAL_POSITION";
    private static final String DEFAULT_VALUE = "COLUMN_DEF";
    private static final String NULLABLE = "NULLABLE";

    public List<Table> readTables(ResultSet loadedDbInfo) {
        List<Table> loadedTables = new ArrayList<>();

        try {
            while (loadedDbInfo.next()) {
                String tableName = loadedDbInfo.getString(TABLE_NAME);
                String tableType = loadedDbInfo.getString(TABLE_TYPE);
                String catalogName = loadedDbInfo.getString(CATALOG_NAME);
                String schemaName = loadedDbInfo.getString(SCHEMA_NAME);
                String remarks = loadedDbInfo.getString(REMARKS);
                loadedTables.add(new Table(tableName, tableType, catalogName,
                    schemaName, remarks));
            }
        }
        catch (SQLException ex) {
            UnableLoadDbException.UnableLoadDbException("Failed to upload table metadata. " + ex.getMessage());
        }

        return loadedTables;
    }

    public List<TableColumn> readColumns(ResultSet loadedDbInfo) {
        List<TableColumn> loadedTableColumns = new ArrayList<>();
        try {
            while (loadedDbInfo.next()) {
                String columnName = loadedDbInfo.getString(COLUMN_NAME);
                String tableName = loadedDbInfo.getString(TABLE_NAME);
                String dataType = loadedDbInfo.getString(DATA_TYPE);
                String position = loadedDbInfo.getString(POSITION);
                String defaultValue = loadedDbInfo.getString(DEFAULT_VALUE);
                String nullable = loadedDbInfo.getString(NULLABLE);
                String remarks = loadedDbInfo.getString(REMARKS);
                loadedTableColumns
                    .add(new TableColumn(tableName, columnName, position,
                        defaultValue, nullable, dataType,
                        remarks));
            }
        }
        catch (SQLException ex) {
            UnableLoadDbException.UnableLoadDbException("Failed to upload table columns metadata. " + ex.getMessage());
        }

        return loadedTableColumns;
    }
}
