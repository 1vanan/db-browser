package com.example.dbbrowser.service;

import com.example.dbbrowser.DbBrowserAbstractTest;
import com.example.dbbrowser.repository.ConnectionPropertiesRepository;
import com.example.dbbrowser.utils.PostgressqlConnecter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import org.junit.ClassRule;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.mockito.Mockito.when;

/*
 Test includes communication with PostgreSQLContainer to mock connection to Postgres database.
 To run test correctly, run Docker.
 */
@Testcontainers
@SpringBootTest
public class DbBrowsingServiceTest extends DbBrowserAbstractTest {
    @Mock
    private ConnectionPropertiesRepository propertiesRepository;
    @Mock
    private PostgressqlConnecter postgressqlConnecter;
    @InjectMocks
    private DbBrowsingServiceImpl dbBrowsingService;
    static Connection mockcon;

    @BeforeAll
    static void setUp() throws SQLException {
        mockcon = postgreSQLContainer.createConnection("");
        mockcon.setSchema("schema name");
    }

    @BeforeEach
    void init() {
        mock();
    }

    @AfterAll
    static void tearDown() throws SQLException {
        mockcon.close();
    }

    @ClassRule
    @Container
    public static PostgreSQLContainer postgreSQLContainer =
        new PostgreSQLContainer("postgres")
        .withDatabaseName(NAME1)
        .withUsername(USERNAME1)
        .withPassword(PASSWORD1);

    static class Initializer
        implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    void findAllSchemasTest() {
        assertTrue(dbBrowsingService.findAllSchemas(1L).size() > 0);
    }

    private void mock(){
        when(postgressqlConnecter.createConnection(properties1)).
            thenReturn(mockcon);

        when(propertiesRepository.findById(properties1.getId())).
            thenReturn(Optional.of(properties1));
    }
}
