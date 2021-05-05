package com.example.dbbrowser.controller;

import com.example.dbbrowser.DbBrowserAbstractTest;
import com.example.dbbrowser.service.ConnectionPropertiesServiceImpl;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RunWith(JUnitPlatform.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BrowserControllerTest extends DbBrowserAbstractTest {
    @Value("http://localhost:${local.server.port}/ataccama/db-browser/connections")
    private String baseUrl;
    @Autowired
    private MockMvc mvc;
    @MockBean
    private ConnectionPropertiesServiceImpl service;

    @Test
    void testCreateProperty() throws Exception {
        when(service.save(properties1)).thenReturn(properties1);

        mvc.perform(post(baseUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(properties1)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is(properties1.getName())));
    }

    @Test
    void testFindProperties() throws Exception {
        when(service.findAllProperties()).thenReturn(Arrays.asList(properties1, properties2));

        mvc.perform(get(baseUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(properties1)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$..name", is(Arrays.asList(properties1.getName(), properties2.getName()))));
    }

    @Test
    void testUpdateProperty() throws Exception {
        when(service.updateProperties(properties1)).thenReturn(properties1);

        mvc.perform(put(baseUrl)
            .contentType(MediaType.APPLICATION_JSON)
            .content(toJson(properties1)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is(properties1.getName())));
    }

    @Test
    void testDeleteProperty() throws Exception {
        doNothing().when(service).deleteProperties(properties1.getId());

        mvc.perform(delete(baseUrl + "/" + properties1.getId())
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

    public byte[] toJson(Object object) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        return mapper.writeValueAsBytes(object);
    }

}
