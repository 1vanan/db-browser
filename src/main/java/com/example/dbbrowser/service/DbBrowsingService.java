package com.example.dbbrowser.service;

import com.example.dbbrowser.dto.Table;
import com.example.dbbrowser.dto.TableColumn;
import com.example.dbbrowser.dto.TableRecord;
import java.util.List;
import java.util.Optional;

/*
Performs Metadata search operations.
 */
public interface DbBrowsingService {

    List<String> findAllSchemas(Long id);

    List<Table> findAllTables(Long id, Optional<String> schema);

    List<TableColumn> listTableColumns(Long id, Optional<String> schema, Optional<String> table);

    List<TableRecord> listTableRecords(Long id, String schema, String table, Optional<String> by, Optional<String> limit);
}
