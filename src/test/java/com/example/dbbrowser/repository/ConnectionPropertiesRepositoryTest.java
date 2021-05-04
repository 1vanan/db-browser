package com.example.dbbrowser.repository;

import com.example.dbbrowser.DbBrowserAbstractTest;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(JUnitPlatform.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConnectionPropertiesRepositoryTest extends DbBrowserAbstractTest {
    @Autowired
    ConnectionPropertiesRepository connectionPropertiesRepository;

    @BeforeEach
    void setUp() {
        createItems();
    }

    @Test
    public void testFindAll() {
        assertEquals(connectionPropertiesRepository.findAll().get(0), properties1);
        assertEquals(connectionPropertiesRepository.findAll().get(1), properties2);
        assertEquals(connectionPropertiesRepository.findAll().size(), 2);
    }

    @Test
    public void testDelete() {
        assertEquals(connectionPropertiesRepository.findAll().size(), 2);

        connectionPropertiesRepository.deleteById(properties1.getId());
        assertEquals(connectionPropertiesRepository.findAll().size(), 1);

        connectionPropertiesRepository.deleteById(properties2.getId());
        assertEquals(connectionPropertiesRepository.findAll().size(), 0);
    }

    private void createItems() {
        connectionPropertiesRepository.saveAll(Arrays.asList(properties1, properties2));
    }
}
