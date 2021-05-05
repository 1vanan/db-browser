package com.example.dbbrowser.controller;

import com.example.dbbrowser.dto.ConnectionProperties;
import com.example.dbbrowser.dto.Table;
import com.example.dbbrowser.dto.TableColumn;
import com.example.dbbrowser.dto.TableRecord;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface BrowserApi {
    List<ConnectionProperties> listProperties();

    ConnectionProperties addProperties(ConnectionProperties properties);

    ConnectionProperties updateProperties(ConnectionProperties properties);

    void deleteProperties(Long id);

    List<String> listSchemas(Long id);

    List<Table> listTables(Long id, Optional<String> schema);

    List<TableColumn> listTableColumns(@PathVariable Long id,
        @RequestParam Optional<String> schema,
        @RequestParam Optional<String> table);

    List<TableRecord> viewTable(@PathVariable Long id,
        @NotBlank @RequestParam String schema,
        @NotBlank @RequestParam String table,
        @RequestParam Optional<String> orderBy,
        @RequestParam Optional<String> limit);
}
