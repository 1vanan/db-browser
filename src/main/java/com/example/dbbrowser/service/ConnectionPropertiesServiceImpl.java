package com.example.dbbrowser.service;

import com.example.dbbrowser.dto.ConnectionProperties;
import com.example.dbbrowser.exceptions.PropertyNotFoundException;
import com.example.dbbrowser.repository.ConnectionPropertiesRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConnectionPropertiesServiceImpl implements ConnectionPropertiesService {
    private final ConnectionPropertiesRepository propertiesRepository;

    @Override
    public ConnectionProperties save(ConnectionProperties properties) {
        return propertiesRepository.save(properties);
    }

    @Override
    public List<ConnectionProperties> findAllProperties() {
        return propertiesRepository.findAll();
    }

    @Override
    public ConnectionProperties updateProperties(ConnectionProperties newProperties) {
        return propertiesRepository.findById(newProperties.getId())
            .map(properties -> {
                properties.setId(newProperties.getId());
                properties.setName(newProperties.getName());
                properties.setHostname(newProperties.getHostname());
                properties.setPort(newProperties.getPort());
                properties.setDbname(newProperties.getDbname());
                properties.setUsername(newProperties.getUsername());
                properties.setPassword(newProperties.getPassword());
                return propertiesRepository.save(properties);
            })
            .orElseThrow(() -> new PropertyNotFoundException("update failed on id: " + newProperties.getId()));
    }

    @Override
    public void deleteProperties(Long id) {
        propertiesRepository.findById(id)
            .orElseThrow(() -> new PropertyNotFoundException("deletion failed on id: " + id));
        propertiesRepository.deleteById(id);
    }
}
