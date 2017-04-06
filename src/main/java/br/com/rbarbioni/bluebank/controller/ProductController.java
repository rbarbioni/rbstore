package br.com.rbarbioni.bluebank.controller;

import br.com.rbarbioni.bluebank.model.Account;
import br.com.rbarbioni.bluebank.model.AccountHistory;
import br.com.rbarbioni.bluebank.model.Product;
import br.com.rbarbioni.bluebank.model.dto.AccountTransferDto;
import br.com.rbarbioni.bluebank.service.AccountHistoryService;
import br.com.rbarbioni.bluebank.service.AccountService;
import br.com.rbarbioni.bluebank.service.ProductService;
import br.com.rbarbioni.bluebank.util.Cpf;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

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
    public List<Product> findAll (){
        return this.productService.findAll();
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public Product findById (@PathVariable Long id){
        return this.productService.findById(id);
    }


    @RequestMapping(value = "/checkout", method = {RequestMethod.POST})
    public void checkout (){

        return this.accountHistoryService.find(cpf, agencia, numero);

    }
}
