package br.com.rbarbioni.rbstore.controller;

import br.com.rbarbioni.rbstore.model.CheckoutRequest;
import br.com.rbarbioni.rbstore.model.CheckoutDiscount;
import br.com.rbarbioni.rbstore.service.CheckoutService;
import br.com.rbarbioni.rbstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created by renan on 10/02/2017.
 */

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    @Autowired
    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }



    @RequestMapping(value = "/calc", method = {RequestMethod.POST})
    public CheckoutDiscount checkout (@Valid @RequestBody CheckoutRequest checkoutRequest){
        return this.checkoutService.calc(checkoutRequest);
    }
}