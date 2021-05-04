package com.example.dbbrowser.repository;

import com.example.dbbrowser.dto.ConnectionProperties;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConnectionPropertiesRepository extends JpaRepository<ConnectionProperties, Long> {
}
