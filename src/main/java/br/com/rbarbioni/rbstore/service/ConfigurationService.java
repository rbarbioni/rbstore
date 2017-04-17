package br.com.rbarbioni.rbstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by renan on 17/04/17.
 */
@Service
public class ConfigurationService {

    private final Environment environment;

    @Autowired
    public ConfigurationService(Environment environment) {
        this.environment = environment;
    }

    public Map<String, Object> findAll (){
        Map<String, Object> configurations = new HashMap();
        configurations.put("publicKey", environment.getProperty("security.moip.public_key"));
        return configurations;
    }
}

