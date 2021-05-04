package com.example.dbbrowser.controller;

import com.example.dbbrowser.dto.ConnectionProperties;
import com.example.dbbrowser.dto.Schema;
import com.example.dbbrowser.exceptions.PropertyVerificationException;
import com.example.dbbrowser.service.ConnectionPropertiesService;
import java.util.List;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ataccama/db-browser/properties")
public class BrowserController implements BrowserApi {
    // change it in case of usage another type of hostname.
    Pattern hostnamePattern = Pattern.compile(".*");
    // This will make sure that any given string is numeric and between the range of 0 and 65535.
    Pattern portPattern = Pattern.compile
        ("^([0-9]{1,4}|[1-5][0-9]{4}|6[0-4][0-9]{3}|65[0-4][0-9]{2}|655[0-2][0-9]|6553[0-5])$");

    private final ConnectionPropertiesService propertiesService;

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ConnectionProperties> listProperties() {
        return propertiesService.findAllProperties();

    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConnectionProperties addProperties(@RequestBody ConnectionProperties properties) {
        verifyProperties(properties);

        return propertiesService.save(properties);
    }

    @Override
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ConnectionProperties updateProperties(@RequestBody ConnectionProperties properties) {
        verifyProperties(properties);

        return propertiesService.updateProperties(properties);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProperties(@PathVariable Long id) {
        propertiesService.deleteProperties(id);
    }

    private void verifyProperties(ConnectionProperties properties) {
        if (!hostnamePattern.matcher(properties.getHostname()).matches()) {
            PropertyVerificationException.PropertyVerificationException("hostname", properties.getHostname());
        }

        if (!portPattern.matcher(properties.getPort()).matches()) {
            PropertyVerificationException.PropertyVerificationException("port", properties.getPort());
        }
    }

    @Override
    @GetMapping("/schemas")
    @ResponseStatus(HttpStatus.OK)
    public List<String> listSchemas() {
        return propertiesService.findAllSchemas();
    }
}
