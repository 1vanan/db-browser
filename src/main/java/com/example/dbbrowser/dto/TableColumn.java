package com.example.dbbrowser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TableColumn {
    protected String tableName;
    protected String columnName;
    protected String position;
    protected String defaultValue;
    protected String nullable;
    protected String dataType;
    protected String remarks;
}
