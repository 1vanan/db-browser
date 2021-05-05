package com.example.dbbrowser.service;

import com.example.dbbrowser.DbBrowserAbstractTest;
import com.example.dbbrowser.exceptions.PropertyNotFoundException;
import com.example.dbbrowser.repository.ConnectionPropertiesRepository;
import java.util.Arrays;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/*
Checks that CRUD operations work correctly.
Also, cheks on the correct working of exceptions.
 */
@SpringBootTest
public class ConnectionPropertiesServiceImplTest extends DbBrowserAbstractTest {
    @Mock
    private ConnectionPropertiesRepository propertiesRepository;
    @InjectMocks
    private ConnectionPropertiesServiceImpl connectionPropertiesService;

    @BeforeEach
    void setUp() {
        createItems();
    }

    @Test
    void testFindAllProperties() {
        assertTrue(connectionPropertiesService.findAllProperties().contains(properties1));
        assertTrue(connectionPropertiesService.findAllProperties().contains(properties2));
    }

    @Test
    void testUpdateProperty() {
        connectionPropertiesService.updateProperties(properties1);
        connectionPropertiesService.updateProperties(properties2);
    }

    @Test
    void testUpdatePropertyWithException() {
        assertThrows(PropertyNotFoundException.class, () -> {
            connectionPropertiesService.updateProperties(propertiesFake);
        });
    }

    @Test
    void testDeleteProperty() {
        connectionPropertiesService.deleteProperties(properties1.getId());
        connectionPropertiesService.deleteProperties(properties2.getId());
    }

    @Test
    void testDeletePropertyWithException() {
        assertThrows(PropertyNotFoundException.class, () -> {
            connectionPropertiesService.deleteProperties(propertiesFake.getId());
        });
    }

    private void createItems() {
        when(propertiesRepository.findAll()).
            thenReturn(Arrays.asList(properties1, properties2));

        when(propertiesRepository.findById(properties1.getId())).
            thenReturn(Optional.of(properties1));

        when(propertiesRepository.findById(properties2.getId())).
            thenReturn(Optional.of(properties2));

        when(propertiesRepository.save(properties1)).
            thenReturn(properties1);

        when(propertiesRepository.save(properties2)).
            thenReturn(properties2);

        doNothing().when(propertiesRepository).deleteById(properties1.getId());
        doNothing().when(propertiesRepository).deleteById(properties2.getId());
    }
}
