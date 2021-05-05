package com.example.dbbrowser.dto;

import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableRecord {
    private Set<Record> values;

    public void addRecord(Record value) {
        this.values.add(value);
    }

}
