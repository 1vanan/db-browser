package com.example.dbbrowser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
Table Colimn metadata.
 */
@Data
@AllArgsConstructor
public class TableColumn {
    // Name of table that keeps current column.
    protected String tableName;
    // Name of current column.
    protected String columnName;
    // Ordinal position of column in table.
    protected String position;
    protected String defaultValue;
    protected String nullable;
    protected String dataType;
    protected String comments;
}
