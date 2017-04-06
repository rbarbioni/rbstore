package br.com.rbarbioni.bluebank.service;

import br.com.rbarbioni.bluebank.model.Product;
import br.com.rbarbioni.bluebank.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by renan on 05/04/2017.
 */

@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll(){
        return this.productRepository.findAll();
    }

    public Product findById(final Long id){
        return this.productRepository.findById(id);
    }

    public void checkout(){

    }
}