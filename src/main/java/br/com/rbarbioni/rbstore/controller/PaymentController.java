package br.com.rbarbioni.rbstore.controller;

import br.com.rbarbioni.rbstore.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

/**
 * Created by renan on 14/04/17.
 */

@RestController
@RequestMapping("/secure/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @Autowired
    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET})
    public Map<String, Object> find(@PathVariable String id) throws IOException {
        return this.paymentService.findStatus(id);
    }
}
