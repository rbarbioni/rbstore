package br.com.rbarbioni.rbstore.repository;

import br.com.rbarbioni.rbstore.model.Product;
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
public class ProductRepository {

    private static final Logger logger = LoggerFactory.getLogger(ProductRepository.class);

    private static final Map<Long, Product> PRODUCTS = Collections.synchronizedMap(new HashMap());

    private final Resource resource;

    private final ObjectMapper objectMapper;

    @Autowired
    public ProductRepository(@Value("classpath:products.json")Resource resource, ObjectMapper objectMapper) {
        this.resource = resource;
        this.objectMapper = objectMapper;
    }

    public Collection<Product> findAll(){
        try {
            InputStream inputStream = resource.getInputStream();
            Product[] products = this.objectMapper.readValue(inputStream, Product[].class);

            for (Product product : products) {
                PRODUCTS.put(product.getId(), product);
            }

        } catch (IOException e) {
            logger.error("Error initialize PROPERTIES source classpath:properties.json", e);
        }

        return PRODUCTS.values();
    }

    public Product findById(Long id){
        return PRODUCTS.get(id);
    }
}
