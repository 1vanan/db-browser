package com.example.dbbrowser.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
DTO for hadling exceptions.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionResponse {
    private int status;
    private String description;
}
