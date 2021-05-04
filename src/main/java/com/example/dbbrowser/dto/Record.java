package com.example.dbbrowser.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Record {
    private String columnName;
    private String value;
}
