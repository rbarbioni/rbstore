package br.com.rbarbioni.rbstore.controller;

import br.com.rbarbioni.rbstore.model.Product;
import br.com.rbarbioni.rbstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * Created by renan on 10/02/2017.
 */

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(method = {RequestMethod.GET})
    public Collection<Product> findAll (){
        return this.productService.findAll();
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public Product findById (@PathVariable Long id){
        return this.productService.findById(id);
    }
}