package com.example.dbbrowser.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ConnectionProperties{
    @Id
    @GeneratedValue
    Long id;

    String name;

    String hostname;

    String port;

    String dbname;

    String username;

    String password;
}
