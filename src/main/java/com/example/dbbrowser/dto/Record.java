package com.example.dbbrowser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/*
One record in table.
Record of whole table in @TableRecord.
 */
@Data
@AllArgsConstructor
public class Record {
    private String columnName;
    private String value;
}
