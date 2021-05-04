package com.example.dbbrowser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Table {
    private String tableName;
    private String tableType;
    private String catalogName;
    private String schemaName;
    private String remarks;
}
