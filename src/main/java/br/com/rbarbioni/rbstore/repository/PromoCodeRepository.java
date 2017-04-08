package br.com.rbarbioni.rbstore.repository;

import br.com.rbarbioni.rbstore.model.PromoCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by renan on 05/04/2017.
 */

@Component
public class PromoCodeRepository {

    private static final Logger logger = LoggerFactory.getLogger(PromoCodeRepository.class);

    private static final Map<String, PromoCode> CODES = Collections.synchronizedMap(new HashMap());

    private final Resource resource;

    private final ObjectMapper objectMapper;

    @Autowired
    public PromoCodeRepository(@Value("classpath:codes.json")Resource resource, ObjectMapper objectMapper) {
        this.resource = resource;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init (){
        try {
            InputStream inputStream = resource.getInputStream();
            PromoCode[] codes = this.objectMapper.readValue(inputStream, PromoCode[].class);

            for (PromoCode promoCode : codes) {
                CODES.put(promoCode.getCode(), promoCode);
            }

        } catch (IOException e) {
            logger.error("Error initialize PROPERTIES source classpath:properties.json", e);
        }

    }

    public Collection<PromoCode> findAll(){
        return CODES.values();
    }

    public PromoCode findById(String code){
        return CODES.get(code);
    }
}