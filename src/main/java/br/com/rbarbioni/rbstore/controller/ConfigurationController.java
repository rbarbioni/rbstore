package br.com.rbarbioni.rbstore.controller;

import br.com.rbarbioni.rbstore.service.ConfigurationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

/**
 * Created by renan on 17/04/17.
 */
@RestController
@RequestMapping("/secure/api/configuration")
public class ConfigurationController {

    private final ConfigurationService configurationService;

    @Autowired
    public ConfigurationController(ConfigurationService configurationService) {
        this.configurationService = configurationService;
    }

    @RequestMapping(method = {RequestMethod.GET})
    public Map<String, Object> findAll () throws IOException {
        return this.configurationService.findAll();
    }
}